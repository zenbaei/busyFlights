package com.travix.medusa.busyFlights.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyFlights.domain.Flight;
import com.travix.medusa.busyFlights.service.FlightService;
import com.travix.medusa.common.constant.Constants.APIPath;
import com.travix.medusa.common.dto.FlightSearchDTO;

/**
 * REST endpoint for flight search API.
 *
 * @author islam zenbaei
 *
 */
@RestController
@RequestMapping(FlightsAPIPath.BASE)
public class FlightController {

	private final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);
	private final FlightService flightService;

	@Autowired
	public FlightController(final FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping(FlightsAPIPath.SEARCH)
	public List<Flight> searchFlight(@Valid final FlightSearchDTO flightSearchDTO) {
		LOGGER.debug("Get request searchFlight with parameters [{}]", flightSearchDTO);
		return flightService.searchFlights(flightSearchDTO);
	}
}

/**
 * Rest API paths.
 *
 * @author islam zenbaei
 *
 */
class FlightsAPIPath {
	public static final String BASE = APIPath.BASE_API_V1 + "/flights";
	public static final String SEARCH = "/search";
}