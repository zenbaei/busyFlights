package com.travix.medusa.busyFlights.service;
import static com.travix.medusa.helper.FlightSearchTestHelper.ARRIVAL_TIME_STR;
import static com.travix.medusa.helper.FlightSearchTestHelper.DEPARTURE_TIME_STR;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.travix.medusa.SpringBaseTest;
import com.travix.medusa.SpringIntegrationTest;
import com.travix.medusa.busyFlights.domain.Flight;
import com.travix.medusa.common.dto.FlightSearchDTO;
import com.travix.medusa.common.type.Airline;
import com.travix.medusa.common.type.IATA;
import com.travix.medusa.common.type.Supplier;
import com.travix.medusa.supplier.SupplierService;

@SpringIntegrationTest
public class FlightServiceImplTest extends SpringBaseTest {

	@Autowired
	private FlightService flightService;

	@MockBean
	@Qualifier("crazyAirServiceImpl")
	private SupplierService crazyAirSupplierService;

	@MockBean
	@Qualifier("toughJetServiceImpl")
	private SupplierService toughJetSupplierService;

	static final double CRAZY_AIR_PRICE = 200.22;
	static final double TOUGH_JET_PRICE = 149.22;

	static final Flight FLIGHT_1 = new Flight(Airline.KLM, Supplier.CRAZY_AIR, CRAZY_AIR_PRICE,
			IATA.CAIRO_AIRPORT, IATA.AMSTERDAM_AIRPORT_SCHIPHOL,
			LocalDateTime.parse(DEPARTURE_TIME_STR), LocalDateTime.parse(ARRIVAL_TIME_STR));

	static final Flight FLIGHT_2 = new Flight(Airline.JET_AIRWAYS, Supplier.TOUGH_JET, TOUGH_JET_PRICE,
			IATA.CAIRO_AIRPORT, IATA.AMSTERDAM_AIRPORT_SCHIPHOL,
			LocalDateTime.parse(DEPARTURE_TIME_STR), LocalDateTime.parse(ARRIVAL_TIME_STR));

	@Test
	public void flightService_shouldbeInjected() {
		assertNotNull(flightService);
	}

	@Before
	public void setup() {
		when(crazyAirSupplierService.searchFlights(nullable(FlightSearchDTO.class))).
			thenReturn(Arrays.asList(FLIGHT_1));

		when(toughJetSupplierService.searchFlights(nullable(FlightSearchDTO.class))).
		thenReturn(Arrays.asList(FLIGHT_2));
	}

	@Test
	public void searchFlight_whenSuppliersSearchesAreProvided_thenAggregateResultIntoListOfFlight() {
		final List<Flight> flights = flightService.searchFlights(null);
		assertThat(flights.size(), is(2));
	}

	@Test
	public void searchFlight_whenSuppliersSearchesAreProvided_thenOrderResultByFareAsc() {
		final List<Flight> flights = flightService.searchFlights(null);

		assertThat(flights.get(0).getSupplier(), is(Supplier.TOUGH_JET));
		assertThat(flights.get(0).getFare(), is(TOUGH_JET_PRICE));

		assertThat(flights.get(1).getSupplier(), is(Supplier.CRAZY_AIR));
		assertThat(flights.get(1).getFare(), is(CRAZY_AIR_PRICE));
	}
}
