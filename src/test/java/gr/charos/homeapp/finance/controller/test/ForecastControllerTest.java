package gr.charos.homeapp.finance.controller.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import gr.charos.homeapp.finance.controller.ForecastController;
import gr.charos.homeapp.finance.repository.PersistentFamilyRepository;

public class ForecastControllerTest extends SpringAwareTest {
	
	private static final String FAMILY_ID = "A_FAMILY_ID";
	
	
	@Mock
	@Autowired
	PersistentFamilyRepository familyRepository;
	
	
	@Autowired
	@InjectMocks
	ForecastController forecastController;
	
	
	@Before
	public void initMocks(){
		Mockito.when(familyRepository.findOne(FAMILY_ID)).thenReturn(null);
	}
	
	@Test
	public void testAddForecast() {
		
	}

}
