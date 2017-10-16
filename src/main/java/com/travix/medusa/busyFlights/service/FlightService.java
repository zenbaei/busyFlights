package com.travix.medusa.busyFlights.service;

import java.util.List;

import com.travix.medusa.busyFlights.domain.Flight;
import com.travix.medusa.common.dto.FlightSearchDTO;

/**
 * Interface for flights related services.
 *
 * @author islam zenbaei
 *
 */
public interface FlightService {

	/**
	 * Searches flights with the given criteria expressed in {@code FlightSearchDTO}.
	 *
	 * <p>
	 * 	Note that price of flights is rounded to 2 decimals.
	 * </p>
	 *
	 * @param flightSearchDTO
	 *
	 * @return a list of flights ordered by fare ascending or empty list when no result
	 */
	List<Flight> searchFlights(FlightSearchDTO flightSearchDTO);

}
