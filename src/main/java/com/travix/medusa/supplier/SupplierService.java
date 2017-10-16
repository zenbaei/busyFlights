package com.travix.medusa.supplier;

import java.util.List;

import org.springframework.web.client.RestClientException;

import com.travix.medusa.busyFlights.domain.Flight;
import com.travix.medusa.common.dto.FlightSearchDTO;

/**
 * Interface to be implemented by flights search suppliers.
 *
 * @author islam zenbaei
 *
 */
public interface SupplierService {

	/**
	 * Searches a flight using the criteria expressed in {@code FlightSearchDTO}.
	 *
	 * @param flightSearchDTO
	 *
	 * @return a list of flights or empty list when no result
	 *
	 * @throws RestClientException in case the supplier service is retrieved from an endpoint
	 */
	List<Flight> searchFlights(FlightSearchDTO flightSearchDTO);

}
