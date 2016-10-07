package gr.charos.homeapp.finance.dto;

import java.util.HashSet;
import java.util.Set;

import gr.charos.homeapp.commons.model.House;
import gr.charos.homeapp.commons.model.Member;
import gr.charos.homeapp.commons.model.Vehicle;

public class FamilyDTO {
	private String id;
	private String familyName;
	private HouseDTO house;
	private Set<MemberDTO> members;
	private Set<HouseDTO> vacationHouses;
	private Set<VehicleDTO> vehicles;
	public String getFamilyName() {
		return familyName;
	}
	public FamilyDTO setFamilyName(String familyName) {
		this.familyName = familyName;
		return this;
	}
	public HouseDTO getHouse() {
		return house;
	}
	public FamilyDTO setHouse(HouseDTO house) {
		this.house = house;
		return this;
	}
	public Set<MemberDTO> getMembers() {
		if (members == null) {
			members = new HashSet<MemberDTO>();
		}
		return members;
	}
	public FamilyDTO setMembers(Set<MemberDTO> members) {
		this.members = members;
		return this;
	}
	public Set<HouseDTO> getVacationHouses() {
		if (vacationHouses == null) {
			vacationHouses = new HashSet<HouseDTO>();
		}
		return vacationHouses;
	}
	public FamilyDTO setVacationHouses(Set<HouseDTO> vacationHouses) {
		this.vacationHouses = vacationHouses;
		return this;
	}
	public Set<VehicleDTO> getVehicles() {
		if (vehicles == null) {
			vehicles = new HashSet<VehicleDTO>();
		}
		return vehicles;
	}
	public FamilyDTO setVehicles(Set<VehicleDTO> vehicles) {
		this.vehicles = vehicles;
		return this;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
