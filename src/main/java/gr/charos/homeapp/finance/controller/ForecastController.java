package gr.charos.homeapp.finance.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gr.charos.homeapp.commons.model.Spender;
import gr.charos.homeapp.commons.model.forecast.Forecast;
import gr.charos.homeapp.finance.domain.PersistentFamily;
import gr.charos.homeapp.finance.dto.ForecastDTO;
import gr.charos.homeapp.finance.repository.PersistentFamilyRepository;
import gr.charos.homeapp.finance.utils.FamilyUtil;
import gr.charos.homeapp.finance.utils.UUIDGenerator;

@RestController
@RequestMapping("/api/forecast")
@CrossOrigin
public class ForecastController {

	@Autowired
	PersistentFamilyRepository familyRepository;

	@Autowired
	ModelMapper modelMapper;
	
	public ForecastController(PersistentFamilyRepository familyRepository, ModelMapper modelMapper) {
		this.familyRepository = familyRepository ;
		this.modelMapper = modelMapper;
	}

	@RequestMapping(value = "/incoming/{familyId}", method = RequestMethod.GET)
	public Set<ForecastDTO> getIncomingForecasts(@PathVariable String familyId) {
		PersistentFamily family = familyRepository.findById(familyId).get();
		return family.getIncomingForecasts().stream().map(forecast -> modelMapper.map(forecast, ForecastDTO.class))
				.collect(Collectors.toSet());
	}

	@RequestMapping(value = "/outgoing/{familyId}", method = RequestMethod.GET)
	public Set<ForecastDTO> getOutgoingForecasts(@PathVariable String familyId) {
		PersistentFamily family = familyRepository.findById(familyId).get();
		return family.getOutgoingForecasts().stream().map(forecast -> modelMapper.map(forecast, ForecastDTO.class))
				.collect(Collectors.toSet());
	}

	@RequestMapping(value = "/incoming/{familyId}/{memberCode}", method = RequestMethod.GET)
	public Set<ForecastDTO> getIncomingForecasts(@PathVariable String familyId, @PathVariable String memberCode) {
		PersistentFamily family = familyRepository.findById(familyId).get();

		Spender spender = FamilyUtil.getSpenderByMemberCode(family, memberCode);

		return spender.getIncomingForecasts().stream().map(forecast -> modelMapper.map(forecast, ForecastDTO.class))
				.collect(Collectors.toSet());
	}

	@RequestMapping(value = "/outgoing/{familyId}/{memberCode}", method = RequestMethod.GET)
	public Set<ForecastDTO> getOutgoingForecasts(@PathVariable String familyId, @PathVariable String memberCode) {
		PersistentFamily family = familyRepository.findById(familyId).get();
		Spender spender = FamilyUtil.getSpenderByMemberCode(family, memberCode);

		return spender.getOutgoingForecasts().stream().map(forecast -> modelMapper.map(forecast, ForecastDTO.class))
				.collect(Collectors.toSet());
	}

	@RequestMapping(value = "/{familyId}/{memberCode}", method = RequestMethod.POST)
	public ForecastDTO addForecast(@PathVariable String familyId, @PathVariable String memberCode,
			@RequestBody ForecastDTO forecast) {
		PersistentFamily family = familyRepository.findById(familyId).get();
		Spender spender = FamilyUtil.getSpenderByMemberCode(family, memberCode);

		forecast.setForecastCode(UUIDGenerator.getUUID());
		spender.getForecasts().add(modelMapper.map(forecast, Forecast.class));

		familyRepository.save(family);

		return forecast;
	}

	@RequestMapping(value = "/{familyId}/{memberCode}/{forecastCode}", method = RequestMethod.DELETE)
	public void deleteForecast(@PathVariable String familyId, @PathVariable String memberCode,
			@PathVariable String forecastCode) {
		PersistentFamily family = familyRepository.findById(familyId).get();
		Spender spender = FamilyUtil.getSpenderByMemberCode(family, memberCode);

		for (Forecast f : spender.getForecasts()) {
			if (f.getForecastCode().equals(forecastCode)) {
				spender.getForecasts().remove(f);
				break;
			}
		}

		familyRepository.save(family);

	}

}
