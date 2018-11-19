package gr.charos.homeapp.finance.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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

	@Override
	public String toString() {
		return "TransactionDTO [date=" + date + ", transactionType=" + transactionType + ", ammountInCents="
				+ ammountInCents + ", transactionCode=" + transactionCode + ", description=" + description
				+ ", budgetCode=" + budgetCode + ", adHoc=" + adHoc + "]";
	}

	public static List<TransactionDTO> fromTextToAdhocOutgoing(String text, Date defaultDate) {
		List<TransactionDTO> transactions = new ArrayList<>();
		// get date groups
		List<String> lines = Arrays.asList(text.split("\\r?\\n"));
		Date d = defaultDate;
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (isEmptyLine(line)) {
				continue;
			}
			if (isDate(line)) {
				d = parseDate(line);
				continue;
			}
			Integer amount = parseCost(line);
			if (amount != null) {

				String description = parseDescription(line);
				TransactionDTO dto = new TransactionDTO();
				dto.setAdHoc(true);
				dto.setAmmountInCents(amount);
				dto.setDate(d);
				dto.setDescription(description);
				dto.setTransactionType(TransactionType.outgoing);
				transactions.add(dto);
			}

		}

		return transactions;
	}

	private static String parseDescription(String line) {
		return line.substring(line.indexOf(" ")).trim();

	}

	private static Integer parseCost(String line) {
		try {
			String amountFormatted = line.split(" ")[0];
			Double amountInDouble = Double.parseDouble(amountFormatted.replace(",", "."));
			return new Double(amountInDouble * 100).intValue();
		} catch (Exception ex) {

		}
		return null;

	}

	private static Date parseDate(String date) {
		String[] parts = date.split("/");

		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0].trim()));
		cal.set(Calendar.MONTH, Integer.parseInt(parts[1].trim()) - 1);

		return cal.getTime();
	}

	private static boolean isDate(String line) {
		String[] parts = line.split("/");
		return parts != null && parts.length == 2 && StringUtils.isNumeric(parts[0].trim())
				&& StringUtils.isNumeric(parts[1].trim());
	}

	private static boolean isEmptyLine(String line) {
		return line.trim().length() == 0;
	}

}
