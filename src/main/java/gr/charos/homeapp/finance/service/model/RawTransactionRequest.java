package gr.charos.homeapp.finance.service.model;

import java.util.Date;

public class RawTransactionRequest {
	private Date date;
	private String transactionsAsText;

	public RawTransactionRequest(Date d, String t) {
		this.date = d;
		this.transactionsAsText = t;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTransactionsAsText() {
		return transactionsAsText;
	}

	public void setTransactionsAsText(String transactionsAsText) {
		this.transactionsAsText = transactionsAsText;
	}

}
