package gr.charos.homeapp.finance.controller.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import gr.charos.homeapp.finance.controller.ForecastController;
import gr.charos.homeapp.finance.repository.PersistentFamilyRepository;

public class ForecastControllerTest extends SpringAwareTest {
	
	private static final String FAMILY_ID = "A_FAMILY_ID";
	
	
	@Mock
	PersistentFamilyRepository familyRepository;
	
	
	ModelMapper mm = new ModelMapper();
	
	ForecastController forecastController;
	
	
	@Before
	public void initMocks(){
		Mockito.when(familyRepository.findById(FAMILY_ID)).thenReturn(null);
		forecastController = new ForecastController(familyRepository, mm);
	}
	
	@Test
	public void testAddForecast() {
		
	}

}
