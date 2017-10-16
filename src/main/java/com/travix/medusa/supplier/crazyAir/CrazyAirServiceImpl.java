package com.travix.medusa.supplier.crazyAir;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.travix.medusa.busyFlights.domain.Flight;
import com.travix.medusa.common.dto.FlightSearchDTO;
import com.travix.medusa.common.type.Airline;
import com.travix.medusa.common.type.CabinClass;
import com.travix.medusa.common.type.IATA;
import com.travix.medusa.common.type.Supplier;
import com.travix.medusa.common.util.HttpUtils;
import com.travix.medusa.common.util.MathUtils;
import com.travix.medusa.supplier.SupplierService;

@Service
public class CrazyAirServiceImpl implements SupplierService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrazyAirServiceImpl.class);

	static final String
		ORIGIN = "origin",
		DESTINATION = "destination",
		DEPARTURE_DATE = "departureDate",
		RETURN_DATE = "returnDate",
		PASSENGER_COUNT = "passengerCount";

	private final RestTemplate restTemplate;

	@Value("${supplier.endpoint.search.crazyAir}")
	private String crazyAirSearchEndpoint;

	@Value("${supplier.endpoint.search.fakeApi}")
	private boolean fakeApi;

	@Autowired
	public CrazyAirServiceImpl(final RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public List<Flight> searchFlights(final FlightSearchDTO flightSearchDTO) {
		final ResponseEntity<CrazyAir[]> response = callEndpoint(flightSearchDTO);
		log(response);
		return getFlights(response);
	}

	List<Flight> getFlights(final ResponseEntity<CrazyAir[]> response) {
		if (response.getBody() == null) {
			return Collections.emptyList();
		}
		return Arrays.stream(response.getBody()).
				map(ca -> new Flight(ca.getAirline(), Supplier.CRAZY_AIR,
						MathUtils.roundToTwo.apply(ca.getPrice()),
						ca.getDepartureAirportCode(), ca.getDestinationAirportCode(),
						ca.getDepartureDate(), ca.getArrivalDate())).
				collect(Collectors.toList());
	}

	ResponseEntity<CrazyAir[]> callEndpoint(final FlightSearchDTO flightSearchDTO) {
		final UriComponents uriComponents = UriComponentsBuilder.fromUriString(crazyAirSearchEndpoint).
			queryParam(ORIGIN, flightSearchDTO.getOrigin().getCode()).
			queryParam(DESTINATION, flightSearchDTO.getDestination().getCode()).
			queryParam(DEPARTURE_DATE, flightSearchDTO.getDepartureDate()).
			queryParam(RETURN_DATE, flightSearchDTO.getReturnDate()).
			queryParam(PASSENGER_COUNT, flightSearchDTO.getNumberOfPassengers()).
			build();

		final URI uri = fakeApi ? URI.create(crazyAirSearchEndpoint) : uriComponents.toUri();
		LOGGER.debug("Call CrazyAir endpoint on [{}]", uri);
		return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(HttpUtils.getHeaders()), CrazyAir[].class);
	}

	private void log(final ResponseEntity<CrazyAir[]> response) {
		LOGGER.debug("Crazy Air search result {}", Arrays.stream(response.getBody())
				.map(ca -> ca.toString())
				.reduce((str1, str2) -> String.join(",", str1, str2))
				.get());
	}
}

/**
 * Internal class that gets populated from Crazy Air search endpoint.
 *
 * @author islam zenbaei
 *
 */
class CrazyAir {

    private Airline airline;

    private double price;

    private CabinClass cabinclass;

    private IATA departureAirportCode, destinationAirportCode;

    private LocalDateTime departureDate, arrivalDate;

    public CrazyAir() {
	}

	public CrazyAir(final Airline airline, final double price, final CabinClass cabinclass, final IATA departureAirportCode,
			final IATA destinationAirportCode, final LocalDateTime departureDate, final LocalDateTime arrivalDate) {
		super();
		this.airline = airline;
		this.price = price;
		this.cabinclass = cabinclass;
		this.departureAirportCode = departureAirportCode;
		this.destinationAirportCode = destinationAirportCode;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(final Airline airline) {
		this.airline = airline;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public CabinClass getCabinclass() {
		return cabinclass;
	}

	public void setCabinclass(final CabinClass cabinclass) {
		this.cabinclass = cabinclass;
	}

	public IATA getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(final IATA departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}

	public IATA getDestinationAirportCode() {
		return destinationAirportCode;
	}

	public void setDestinationAirportCode(final IATA destinationAirportCode) {
		this.destinationAirportCode = destinationAirportCode;
	}

	public LocalDateTime getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(final LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDateTime getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(final LocalDateTime arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@Override
	public String toString() {
		return "CrazyAir [airline=" + airline + ", price=" + price + ", cabinclass=" + cabinclass
				+ ", departureAirportCode=" + departureAirportCode + ", destinationAirportCode="
				+ destinationAirportCode + ", departureDate=" + departureDate + ", arrivalDate=" + arrivalDate + "]";
	}

}
