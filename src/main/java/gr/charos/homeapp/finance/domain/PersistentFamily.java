package gr.charos.homeapp.finance.domain;

import org.springframework.data.annotation.Id;

import gr.charos.homeapp.commons.model.Family;

public class PersistentFamily extends Family {
	
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
