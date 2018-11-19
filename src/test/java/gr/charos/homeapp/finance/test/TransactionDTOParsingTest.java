package gr.charos.homeapp.finance.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import gr.charos.homeapp.finance.dto.TransactionDTO;

public class TransactionDTOParsingTest {
	
	@Test
	public void shouldParseATextWithGroupedDates(){
		String text = "12/1 \n"
				+ "20 gift \n"
				+ "21 super market \n"
				+ "\n"
				+ "13/1 \n"
				+ "12.2 super market \n";
		List<TransactionDTO> dtos = TransactionDTO.fromTextToAdhocOutgoing(text, new Date());
		assertNotNull(dtos);
		assertFalse(dtos.isEmpty());
		assertEquals(3,dtos.size());
		dtos.stream().forEach(p-> {
			System.out.println(p);
		});
		
	}
	@Test
	public void shouldParseATextWithNoDates() {
		
	}
	

}
