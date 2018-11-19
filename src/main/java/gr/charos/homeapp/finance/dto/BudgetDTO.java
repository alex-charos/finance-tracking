package gr.charos.homeapp.finance.dto;

import java.util.Date;

public class BudgetDTO {
	
	private String description;
	private Date dateFrom;
	private Date dateTo;
	private String budgetCode;
	private int ammountInCents;

	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getBudgetCode() {
		return budgetCode;
	}
	public void setBudgetCode(String budgetCode) {
		this.budgetCode = budgetCode;
	}
	public int getAmmountInCents() {
		return ammountInCents;
	}
	public void setAmmountInCents(int ammountInCents) {
		this.ammountInCents = ammountInCents;
	}
	

	
	
}
