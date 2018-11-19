package gr.charos.homeapp.finance.controller.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import gr.charos.homeapp.finance.Application;
import gr.charos.homeapp.finance.dto.FamilyDTO;
import gr.charos.homeapp.finance.dto.HouseDTO;
import gr.charos.homeapp.finance.dto.MemberDTO;
import gr.charos.homeapp.finance.dto.VehicleDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@WebAppConfiguration
public abstract class SpringAwareTest {

	 
	public FamilyDTO getFamily() {
		FamilyDTO family = new FamilyDTO();

		MemberDTO member1 = new MemberDTO().setName("Alex");
		MemberDTO member2 = new MemberDTO().setName("Christina");

		family.getMembers().add(member1);
		family.getMembers().add(member2);

		HouseDTO house = new HouseDTO().setAddress("Vironas");
		family.setHouse(house);

		HouseDTO kea = new HouseDTO().setAddress("Kea");

		family.getVacationHouses().add(kea);

		VehicleDTO vehicle = new VehicleDTO().setType("Simple");

		family.getVehicles().add(vehicle);
		return family;
	}

}
