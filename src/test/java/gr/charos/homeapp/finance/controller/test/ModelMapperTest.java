package gr.charos.homeapp.finance.controller.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import gr.charos.homeapp.finance.domain.PersistentFamily;
import gr.charos.homeapp.finance.dto.FamilyDTO;

public class ModelMapperTest extends SpringAwareTest {

	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Test
	public void testFamilyDTOtoPersistentFamilyConversion(){
		FamilyDTO fDto = getFamily();
		
		PersistentFamily persistentFamily = modelMapper.map(fDto, PersistentFamily.class);
		
		assertEquals(fDto.getFamilyName(), persistentFamily.getFamilyName());
		assertEquals(fDto.getHouse().getCode(), persistentFamily.getHouse().getCode());
		assertEquals(fDto.getHouse().getAddress(), persistentFamily.getHouse().getAddress());
		
		assertEquals(fDto.getMembers().size(), persistentFamily.getMembers().size());
		assertEquals(fDto.getVehicles().size(), persistentFamily.getVehicles().size());
		assertEquals(fDto.getVacationHouses().size(), persistentFamily.getVacationHouses().size());
		
		
	}
}
