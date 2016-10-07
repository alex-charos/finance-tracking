package gr.charos.homeapp.finance.dto;

public class HouseDTO extends SpenderDTO {
	private String address;

	public String getAddress() {
		return address;
	}

	public HouseDTO setAddress(String address) {
		this.address = address;
		return this;
	}
	

}
