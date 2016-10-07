package gr.charos.homeapp.finance.dto;

import java.util.Date;

import gr.charos.homeapp.commons.model.props.Properties.TransactionType;

public class TransactionDTO {
	private Date date;
	private TransactionType transactionType;
	private int ammountInCents;
	private String transactionCode;
	private String description;
	private String budgetCode;
	
	private boolean adHoc;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public int getAmmountInCents() {
		return ammountInCents;
	}
	public void setAmmountInCents(int ammountInCents) {
		this.ammountInCents = ammountInCents;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public boolean isAdHoc() {
		return adHoc;
	}
	public void setAdHoc(boolean adHoc) {
		this.adHoc = adHoc;
	}
	public String getBudgetCode() {
		return budgetCode;
	}
	public void setBudgetCode(String budgetCode) {
		this.budgetCode = budgetCode;
	}
	 
	 
	
	

}
