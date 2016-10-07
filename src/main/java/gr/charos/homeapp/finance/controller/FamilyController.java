package gr.charos.homeapp.finance.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gr.charos.homeapp.commons.model.Spender;
import gr.charos.homeapp.finance.domain.PersistentFamily;
import gr.charos.homeapp.finance.dto.FamilyDTO;
import gr.charos.homeapp.finance.repository.PersistentFamilyRepository;
import gr.charos.homeapp.finance.utils.UUIDGenerator;

@RestController
@RequestMapping("/api/family")
@CrossOrigin
public class FamilyController {

	@Autowired
	PersistentFamilyRepository familyRepository;

	@Autowired
	ModelMapper modelMapper;

	
    @RequestMapping(method = RequestMethod.POST)
    public FamilyDTO createFamily(@RequestBody FamilyDTO family) {
		return saveOrUpdate(family);
	}

    @RequestMapping(method = RequestMethod.PUT)
	public FamilyDTO updateFamily(@RequestBody FamilyDTO family) {
		return saveOrUpdate(family);
	}
    @RequestMapping(value="/{familyId}",method = RequestMethod.GET)
	public FamilyDTO getFamily(@PathVariable String familyId) {
		return modelMapper.map(familyRepository.findOne(familyId), FamilyDTO.class);
	}
    @RequestMapping(method = RequestMethod.GET)
   	public List<FamilyDTO> getAll() {
    	List<PersistentFamily> pfs =familyRepository.findAll();
    	
   		return pfs.stream().map(p -> modelMapper.map(p, FamilyDTO.class)).collect(Collectors.toList());
   	}
    @RequestMapping(value="/{familyId}",method = RequestMethod.DELETE)
	public void deleteFamily(FamilyDTO family) {
		throw new RuntimeException("Not Implemented Yet!");
	}
	

	private FamilyDTO saveOrUpdate(FamilyDTO family) {
		PersistentFamily pFamily = modelMapper.map(family, PersistentFamily.class);

		assignCode(pFamily.getHouse());
		for (Spender m : pFamily.getMembers()) {
			assignCode(m);
		}
		for (Spender m : pFamily.getVacationHouses()) {
			assignCode(m);
		}
		for (Spender m : pFamily.getVehicles()) {
			assignCode(m);
		}

		pFamily = familyRepository.save(pFamily);

		return modelMapper.map(pFamily, FamilyDTO.class);
	}

	
	private void assignCode(Spender s) {
		if (s.getCode() == null) {
			s.setCode(UUIDGenerator.getUUID());
		}
	}

}
