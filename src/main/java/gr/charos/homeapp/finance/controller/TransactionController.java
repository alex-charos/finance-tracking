package gr.charos.homeapp.finance.controller;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.charos.homeapp.commons.model.Spender;
import gr.charos.homeapp.commons.model.forecast.Forecast;
import gr.charos.homeapp.commons.model.transaction.AdHocTransaction;
import gr.charos.homeapp.commons.model.transaction.ForecastTransaction;
import gr.charos.homeapp.commons.model.transaction.Transaction;
import gr.charos.homeapp.finance.context.IDTokenContext;
import gr.charos.homeapp.finance.domain.GroupReport;
import gr.charos.homeapp.finance.domain.PersistentFamily;
import gr.charos.homeapp.finance.dto.TransactionDTO;
import gr.charos.homeapp.finance.predicates.DateFilter;
import gr.charos.homeapp.finance.repository.PersistentFamilyRepository;
import gr.charos.homeapp.finance.utils.FamilyUtil;
import gr.charos.homeapp.finance.utils.UUIDGenerator;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin
public class TransactionController {

	@Autowired
	PersistentFamilyRepository familyRepository;
	


	@Autowired
	ModelMapper modelMapper;

	@RequestMapping(value = "/incoming/{familyId}", method = RequestMethod.GET)
	public List<TransactionDTO> getIncomingTransactions(@PathVariable String familyId, @RequestParam(required=true) Long fromDate, @RequestParam(required=true) Long toDate) {
		PersistentFamily family = familyRepository.findOne(familyId);
		return mapTransactions(family.getIncomingTransactions(), fromDate, toDate);
	}
	
	private List<TransactionDTO> mapTransactions(Set<Transaction> transactions, Long fromDate, Long toDate) {
		return transactions.stream()
				.filter( DateFilter.isBetweenDates(new Date(fromDate), new Date(toDate)))
				.map(transaction -> {
					TransactionDTO dto =modelMapper.map(transaction, TransactionDTO.class);
					if (transaction instanceof ForecastTransaction) {
						dto.setDescription(((ForecastTransaction)transaction).getForecastExecuted().getDescription());
						dto.setAdHoc(false);
					} else {
						dto.setAdHoc(true);
					}
					
					return dto ;}).sorted(Comparator.comparing(TransactionDTO::getDate)).
				
				collect(Collectors.toList());
	}
	@RequestMapping(value = "/outgoing", method = RequestMethod.GET)
	public List<TransactionDTO> getOutgoingTransactions(@RequestParam(required=true) Long fromDate, @RequestParam(required=true) Long toDate) {
		/**
		 * TODO: member/family association
		 */
		String username = IDTokenContext.getUsername();
		PersistentFamily family = familyRepository.findByMembersUsername(username).stream().findFirst().get();
		return mapTransactions(family.getOutgoingTransactions(), fromDate, toDate);
	}
	
	@RequestMapping(value = "/outgoing/grouping", method = RequestMethod.GET)
	public List<GroupReport> getOutgoingTransactionsGrouped() {
		 
		String username = IDTokenContext.getUsername();
		PersistentFamily family = familyRepository.findByMembersUsername(username).stream().findFirst().get();
		
		
		GroupReport group1 = new GroupReport(ChronoUnit.DAYS, family.getOutgoingTransactions());
		GroupReport group2 = new GroupReport(ChronoUnit.WEEKS, family.getOutgoingTransactions());
		GroupReport group3 = new GroupReport(ChronoUnit.MONTHS , family.getOutgoingTransactions());
		return Arrays.asList(group1,group2, group3);
	}
	
	@RequestMapping(value = "/incoming/{familyId}/{memberCode}", method = RequestMethod.GET)
	public Set<TransactionDTO> getIncomingTransactions(@PathVariable String familyId, @PathVariable String memberCode) {
		PersistentFamily family = familyRepository.findOne(familyId);

		Spender spender = FamilyUtil.getSpenderByMemberCode(family, memberCode);

		return spender.getIncomingTransactions().stream()
				.map(transaction -> modelMapper.map(transaction, TransactionDTO.class)).collect(Collectors.toSet());
	}

	@RequestMapping(value = "/outgoing/{familyId}/{memberCode}", method = RequestMethod.GET)
	public Set<TransactionDTO> getOutgoingTransactions(@PathVariable String familyId, @PathVariable String memberCode) {
		PersistentFamily family = familyRepository.findOne(familyId);

		Spender spender = FamilyUtil.getSpenderByMemberCode(family, memberCode);

		return spender.getOutgoingTransactions().stream()
				.map(transaction -> modelMapper.map(transaction, TransactionDTO.class)).collect(Collectors.toSet());

	}

	@RequestMapping(value = "/forecast/{familyId}/{forecastCode}", method = RequestMethod.POST)
	public TransactionDTO addForecastTransaction(@PathVariable String familyId, @PathVariable String forecastCode) {
		PersistentFamily family = familyRepository.findOne(familyId);

		Spender spender = FamilyUtil.getSpenderByForecastCode(family, forecastCode);
		Forecast f = FamilyUtil.getForecastByForecastCode(spender.getForecasts(), forecastCode);
		ForecastTransaction t = new ForecastTransaction(UUIDGenerator.getUUID(), new Date(), f);
		spender.getTransactions().add(t);
		familyRepository.save(family);

		return modelMapper.map(t, TransactionDTO.class);
	}

	@RequestMapping(value = "/ad-hoc/{familyId}/{memberCode}", method = RequestMethod.POST)
	public TransactionDTO addAdHocTransaction(@PathVariable String familyId, @PathVariable String memberCode,@RequestBody TransactionDTO transactionDto) {
		PersistentFamily family = familyRepository.findOne(familyId);

		if (null ==  transactionDto.getTransactionType()) {
			throw new IllegalArgumentException("No Transaction Type");
		}
		Spender spender = FamilyUtil.getSpenderByMemberCode(family, memberCode);
		AdHocTransaction t = new AdHocTransaction(UUIDGenerator.getUUID(), transactionDto.getDate(),
				transactionDto.getTransactionType(), transactionDto.getAmmountInCents(), transactionDto.getDescription());
		spender.getTransactions().add(t);

		familyRepository.save(family);

		return modelMapper.map(t, TransactionDTO.class);
	}
	
	
	@RequestMapping(value = "/{familyId}/{transactionCode}", method = RequestMethod.DELETE)
	public void deleteTransaction(@PathVariable String familyId, @PathVariable String transactionCode) {
		PersistentFamily family = familyRepository.findOne(familyId);

		for (Transaction t : family.getHouse().getTransactions()) {
			if (t.getTransactionCode().equals(transactionCode)) {
				family.getHouse().getTransactions().remove(t);
				break;
			}
		}
		for (Spender s : family.getMembers()) {
			for (Transaction t : s.getTransactions()) {
				if (t.getTransactionCode().equals(transactionCode)) {
					s.getTransactions().remove(t);
					break;
				}
			}
		}
		for (Spender s : family.getVehicles()) {
			for (Transaction t : s.getTransactions()) {
				if (t.getTransactionCode().equals(transactionCode)) {
					s.getTransactions().remove(t);
					break;
				}
			}
		}
		for (Spender s : family.getVacationHouses()) {
			for (Transaction t : s.getTransactions()) {
				if (t.getTransactionCode().equals(transactionCode)) {
					s.getTransactions().remove(t);
					break;
				}
			}
		}

	}

}
