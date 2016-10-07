package gr.charos.homeapp.finance.utils;

import java.util.Set;
import java.util.stream.Collectors;

import gr.charos.homeapp.commons.model.Spender;
import gr.charos.homeapp.commons.model.forecast.Forecast;
import gr.charos.homeapp.finance.domain.PersistentFamily;

public class FamilyUtil {
	public static Spender getSpenderByMemberCode(PersistentFamily family, String memberCode) {
		Spender spender = null;
		if (memberCode.equals(family.getHouse().getCode())) {
			spender = family.getHouse();
		}
		for (Spender s : family.getMembers()) {
			if (memberCode.equals(s.getCode())) {
				spender = s;
			}
		}
		for (Spender s : family.getVacationHouses()) {
			if (memberCode.equals(s.getCode())) {
				spender = s;
			}
		}
		for (Spender s : family.getVehicles()) {
			if (memberCode.equals(s.getCode())) {
				spender = s;
			}
		}

		return spender;
	}

	private static boolean isForecastCodeInForecasts(Set<Forecast> forecasts, String forecastCode) {
		boolean exists = false;
		for (Forecast f : forecasts) {
			if (f.getForecastCode().equals(forecastCode)) {
				exists = true;
			}
		}
		return exists;
	}
	public static Forecast getForecastByForecastCode(Set<Forecast> forecasts, String forecastCode) {
		Set<Forecast> fs =forecasts.stream().filter(f->  forecastCode.equals(f.getForecastCode())).collect(Collectors.toSet());
		if (!fs.isEmpty()) {
			return fs.iterator().next();
		}
		return null;
	
	}

	public static Spender getSpenderByForecastCode(PersistentFamily family, String forecastCode) {
		Spender spender = null;
		if (isForecastCodeInForecasts(family.getHouse().getForecasts(), forecastCode)) {
			spender = family.getHouse();
		}

		for (Spender s : family.getMembers()) {
			if (isForecastCodeInForecasts(s.getForecasts(), forecastCode)) {
				spender = s;
			}
		}
		for (Spender s : family.getVacationHouses()) {
			if (isForecastCodeInForecasts(s.getForecasts(), forecastCode)) {
				spender = s;
			}
		}
		for (Spender s : family.getVehicles()) {
			if (isForecastCodeInForecasts(s.getForecasts(), forecastCode)) {
				spender = s;
			}
		}

		return spender;
	}
}
