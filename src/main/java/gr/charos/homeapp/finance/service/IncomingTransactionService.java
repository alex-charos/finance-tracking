package gr.charos.homeapp.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gr.charos.homeapp.commons.model.Spender;
import gr.charos.homeapp.commons.model.transaction.AdHocTransaction;
import gr.charos.homeapp.commons.model.transaction.RawTransactionRequest;
import gr.charos.homeapp.finance.domain.PersistentFamily;
import gr.charos.homeapp.finance.dto.TransactionDTO;
import gr.charos.homeapp.finance.repository.PersistentFamilyRepository;
import gr.charos.homeapp.finance.utils.FamilyUtil;
import gr.charos.homeapp.finance.utils.UUIDGenerator;

public abstract class IncomingTransactionService {
	

	@Autowired
	PersistentFamilyRepository familyRepository;
	
	public void createTransactions(RawTransactionRequest rtr, String familyCode, String memberCode) {
		
		List<TransactionDTO> transactions = TransactionDTO.fromTextToAdhocOutgoing(rtr.getTransactionsAsText(), rtr.getDate());
		PersistentFamily family = familyRepository.findById(familyCode).get();
		transactions.forEach(transactionDto -> {
			if (null ==  transactionDto.getTransactionType()) {
				throw new IllegalArgumentException("No Transaction Type");
			}
			Spender spender = FamilyUtil.getSpenderByMemberCode(family, memberCode);
			AdHocTransaction t = new AdHocTransaction(UUIDGenerator.getUUID(), transactionDto.getDate(),
					transactionDto.getTransactionType(), transactionDto.getAmmountInCents(), transactionDto.getDescription());
			spender.getTransactions().add(t);

		});
		
		familyRepository.save(family);
		
		
	}
	

}
