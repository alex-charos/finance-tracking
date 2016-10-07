package gr.charos.homeapp.finance.dto;

public class VehicleDTO extends SpenderDTO {
	private String type;

	public String getType() {
		return type;
	}

	public VehicleDTO setType(String type) {
		this.type = type;
		return this;
	}

}
