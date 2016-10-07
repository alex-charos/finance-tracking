package gr.charos.homeapp.finance.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gr.charos.homeapp.commons.model.Budget;
import gr.charos.homeapp.finance.domain.PersistentFamily;
import gr.charos.homeapp.finance.dto.BudgetDTO;
import gr.charos.homeapp.finance.dto.FamilyDTO;
import gr.charos.homeapp.finance.repository.PersistentFamilyRepository;

@RestController
@RequestMapping("/api/family/budget")
@CrossOrigin
public class BudgetController {

	
	@Autowired
	PersistentFamilyRepository familyRepository;

	@Autowired
	ModelMapper modelMapper;
	
	
    @RequestMapping(value ="/{familyId}",method = RequestMethod.POST)
    public BudgetDTO createBudget(@PathVariable String familyId, @RequestBody BudgetDTO budget) {
    	PersistentFamily family = familyRepository.findOne(familyId);
    	budget.setBudgetCode(UUID.randomUUID().toString());
    	family.getBudgets().add(modelMapper.map(budget, Budget.class));
    	familyRepository.save(family);
    	return budget;
	}

    @RequestMapping(value ="/{familyId}", method = RequestMethod.PUT)
	public BudgetDTO update(@PathVariable String familyId,@RequestBody BudgetDTO budget) {
    	return saveOrUpdate(familyId, budget);
	}
    private BudgetDTO saveOrUpdate(String familyId, BudgetDTO budget) {
		// TODO Auto-generated method stub
		return null;
	}

	 
    @RequestMapping(value="/{familyId}",method = RequestMethod.GET)
   	public List<BudgetDTO> getAll(@PathVariable String familyId) {
    	PersistentFamily pfs =familyRepository.findOne(familyId);
    	
   		return pfs.getBudgets(). stream().map(p -> modelMapper.map(p, BudgetDTO.class)).collect(Collectors.toList());
   	}
    @RequestMapping(value="/{familyId}/{budgetCode}",method = RequestMethod.DELETE)
	public void deleteBudget(FamilyDTO family) {
		throw new RuntimeException("Not Implemented Yet!");
	}
	
    private BudgetDTO getBudget(String familyId, BudgetDTO budget) {
    	
    	return familyRepository.findOne(familyId).getBudgets()
    			.stream()
    			.filter(p -> p.getBudgetCode().equals(budget))
    			.map(p->modelMapper.map(p, BudgetDTO.class))
    			.collect(Collectors.toList())
    			.iterator()
    			.next();
	
	//	return modelMapper.map(budget, BudgetDTO.class);
	}

}
