package gr.charos.homeapp.finance.domain;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import gr.charos.homeapp.commons.model.transaction.Transaction;
import gr.charos.homeapp.finance.predicates.DateFilter;

public class GroupReport {
	private Map<String, Integer> groupedExpensesInCents;
	private Integer summedExpencesInCents;
	private Date fromDate;
	private Date toDate;
	private ChronoUnit unit;

	public Map<String, Integer> getGroupedExpensesInCents() {
		return groupedExpensesInCents;
	}

	public Integer getSummedExpencesInCents() {
		return summedExpencesInCents;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public ChronoUnit getUnit() {
		return unit;
	}

	public GroupReport(ChronoUnit unit, Collection<Transaction> transactions) {
		ZonedDateTime fromTemplate = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		Date from;
		Date to = new Date();;
		switch (unit) {
		case DAYS:
			from = Date.from(fromTemplate.toInstant());
			break;
		case WEEKS:
			from = Date.from(fromTemplate.with(DayOfWeek.MONDAY).toInstant());
			break;
		case MONTHS:
			from = Date.from(fromTemplate.withDayOfMonth(1).toInstant());
			break;
		default:
			from = new Date();
			break;
		}
		this.fromDate = from;
		this.toDate = to;
		this.unit = unit;
		
		groupedExpensesInCents = transactions.stream().filter(DateFilter.isBetweenDates(from, to)).collect(Collectors.groupingBy(Transaction::getDescription,
				Collectors.summingInt(Transaction::getAmmountInCents)));
		summedExpencesInCents = groupedExpensesInCents.values().stream().mapToInt(Integer::intValue).sum();

	}

}
