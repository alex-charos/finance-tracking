package gr.charos.homeapp.finance.dto;

public class MemberDTO extends SpenderDTO {
	private String name;

	public String getName() {
		return name;
	}

	public MemberDTO setName(String name) {
		this.name = name;
		return this;
	}
	
	

}
