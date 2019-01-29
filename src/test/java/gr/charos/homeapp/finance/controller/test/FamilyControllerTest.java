package gr.charos.homeapp.finance.controller.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import gr.charos.homeapp.finance.config.ModelMapperConfig;
import gr.charos.homeapp.finance.controller.FamilyController;
import gr.charos.homeapp.finance.domain.PersistentFamily;
import gr.charos.homeapp.finance.dto.FamilyDTO;
import gr.charos.homeapp.finance.dto.HouseDTO;
import gr.charos.homeapp.finance.dto.MemberDTO;
import gr.charos.homeapp.finance.dto.VehicleDTO;
import gr.charos.homeapp.finance.repository.PersistentFamilyRepository;


public class FamilyControllerTest extends SpringAwareTest {
	
	
	@Mock
	PersistentFamilyRepository familyRepository;
	
	ModelMapper mm = new ModelMapper();
	
	FamilyController familyController;
	
	
	
	@Before
	public void initMocks() {
		Mockito.when(familyRepository.save(Mockito.any(PersistentFamily.class))).thenAnswer(new Answer<PersistentFamily>() {
		     public PersistentFamily answer(InvocationOnMock invocation) throws Throwable {
		         return (PersistentFamily) invocation.getArguments()[0];
		     }
		 });
		familyController = new FamilyController(familyRepository, mm);
	}
	
	@Test
	public void testCreateFamilyAssignsCodes(){
		assertNotNull(familyRepository);
		assertNotNull(familyController);
		
		
		FamilyDTO family = getFamily();
		
		
		family = familyController.createFamily(family);
		
		assertNotNull(family);
		
		assertNotNull(family.getHouse().getCode());
		
		for (MemberDTO member : family.getMembers()) {
			assertNotNull(member.getCode());
		}
		for (VehicleDTO vehi : family.getVehicles()) {
			assertNotNull(vehi.getCode());
		}
		
		for (HouseDTO s : family.getVacationHouses()) {
			assertNotNull(s.getCode());
		}
		
		Mockito.verify(familyRepository, Mockito.atLeastOnce()).save(Mockito.any(PersistentFamily.class));
		
	}

}
