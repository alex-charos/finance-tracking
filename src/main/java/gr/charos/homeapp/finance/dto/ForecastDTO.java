package gr.charos.homeapp.finance.dto;

import gr.charos.homeapp.commons.model.props.Properties.TransactionType;

public class ForecastDTO {

	private int ammountInCents;
	private String description;
	private int reoccurenceInDays;
	private TransactionType transactionType;
	private String forecastCode;
	
	public int getAmmountInCents() {
		return ammountInCents;
	}
	public void setAmmountInCents(int ammountInCents) {
		this.ammountInCents = ammountInCents;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getReoccurenceInDays() {
		return reoccurenceInDays;
	}
	public void setReoccurenceInDays(int reoccurenceInDays) {
		this.reoccurenceInDays = reoccurenceInDays;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public String getForecastCode() {
		return forecastCode;
	}
	public void setForecastCode(String forecastCode) {
		this.forecastCode = forecastCode;
	}
	
	
	
}
