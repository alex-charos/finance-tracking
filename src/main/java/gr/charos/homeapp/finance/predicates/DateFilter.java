package gr.charos.homeapp.finance.predicates;

import java.util.Date;
import java.util.function.Predicate;

import gr.charos.homeapp.commons.model.transaction.Transaction;

public class DateFilter {
	public static Predicate<Transaction> isBetweenDates(Date fromDate, Date toDate ) {
	    return p -> 
	    (p.getDate().after(  fromDate) || p.getDate().equals( fromDate ) ) 
		&& ( p.getDate().before( toDate ) || p.getDate().equals(  toDate  )) ; 
	 }

}
