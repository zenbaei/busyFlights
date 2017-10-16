package com.travix.medusa.supplier.crazyAir;

import static com.travix.medusa.helper.FlightSearchTestHelper.ARRIVAL_DATE_STR;
import static com.travix.medusa.helper.FlightSearchTestHelper.ARRIVAL_TIME_STR;
import static com.travix.medusa.helper.FlightSearchTestHelper.DEPARTURE_DATE_STR;
import static com.travix.medusa.helper.FlightSearchTestHelper.DEPARTURE_TIME_STR;
import static com.travix.medusa.helper.FlightSearchTestHelper.FLIGHT_SEARCH_DTO;
import static com.travix.medusa.supplier.crazyAir.CrazyAirServiceImpl.DEPARTURE_DATE;
import static com.travix.medusa.supplier.crazyAir.CrazyAirServiceImpl.DESTINATION;
import static com.travix.medusa.supplier.crazyAir.CrazyAirServiceImpl.ORIGIN;
import static com.travix.medusa.supplier.crazyAir.CrazyAirServiceImpl.PASSENGER_COUNT;
import static com.travix.medusa.supplier.crazyAir.CrazyAirServiceImpl.RETURN_DATE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.SpringBaseTest;
import com.travix.medusa.busyFlights.domain.Flight;
import com.travix.medusa.common.type.Airline;
import com.travix.medusa.common.type.CabinClass;
import com.travix.medusa.common.type.IATA;
import com.travix.medusa.supplier.SupplierService;

@RestClientTest(CrazyAirServiceImpl.class)
public class CrazyAirServiceImplTest extends SpringBaseTest {

	private static final CrazyAir CRAZY_AIR_1 = new CrazyAir(Airline.KLM, 200.22, CabinClass.BUSINESS,
			IATA.AMSTERDAM_AIRPORT_SCHIPHOL, IATA.HEATHROW_AIRPORT, LocalDateTime.parse(DEPARTURE_TIME_STR), LocalDateTime.parse(ARRIVAL_TIME_STR));

	private static final CrazyAir CRAZY_AIR_2 = new CrazyAir(Airline.LUFTHANSA, 200.22, CabinClass.ECONOMY,
			IATA.AMSTERDAM_AIRPORT_SCHIPHOL, IATA.HEATHROW_AIRPORT, LocalDateTime.parse(DEPARTURE_TIME_STR), LocalDateTime.parse(ARRIVAL_TIME_STR));

	private String expectedURL;

	@Autowired
	@Qualifier("crazyAirServiceImpl")
	private SupplierService supplierService;

	@Value("${supplier.endpoint.search.crazyAir}")
	private String crazyAirEndpoint;

	@Autowired
    private MockRestServiceServer server;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void service_shouldBeInjected() {
		assertNotNull(supplierService);
		assertTrue(StringUtils.isNotBlank(crazyAirEndpoint));
	}

	@Before
	public void setup() {
		expectedURL = crazyAirEndpoint + StringUtils.join("?", ORIGIN, "=AMS&", DESTINATION,
				"=LHR&", DEPARTURE_DATE, "=", DEPARTURE_DATE_STR, "&", RETURN_DATE, "=", ARRIVAL_DATE_STR,
				"&", PASSENGER_COUNT, "=1");
	}

	@Test
	public void callEndpoint_whenFlightSearchDTOIsProvided_thenConstructQueryParametersFromIt() throws JsonProcessingException {
		server.expect(requestTo(expectedURL))
			.andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

		((CrazyAirServiceImpl)supplierService).callEndpoint(FLIGHT_SEARCH_DTO);
	}

	@Test
	public void callEndpoint_whenSupplierSearchReturnResult_thenConvertIntoCrazyAirArray() throws JsonProcessingException {
		final String crazyAirJsonArr = objectMapper.writeValueAsString(Arrays.asList(CRAZY_AIR_1, CRAZY_AIR_2));

		server.expect(requestTo(expectedURL))
			.andRespond(withSuccess(crazyAirJsonArr, MediaType.APPLICATION_JSON));

		final ResponseEntity<CrazyAir[]> response = ((CrazyAirServiceImpl)supplierService).callEndpoint(FLIGHT_SEARCH_DTO);
		assertThat(response.getBody().length, is(2));
		assertThat(response.getBody()[0].getAirline(), is(Airline.KLM));
		assertThat(response.getBody()[1].getAirline(), is(Airline.LUFTHANSA));
	}

	@Test(expected = RestClientException.class)
	public void callEndpoint_whenFailed_thenThrowRestClientException() throws JsonProcessingException {
		server.expect(requestTo(expectedURL))
			.andRespond(withServerError());

		((CrazyAirServiceImpl)supplierService).callEndpoint(FLIGHT_SEARCH_DTO);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getFlights_whenCrazyAirArrayIsProvided_thenConvertIntoListOfFlight() {
		final ResponseEntity<CrazyAir[]> response = mock(ResponseEntity.class);
		when(response.getBody()).thenReturn(new CrazyAir[]{CRAZY_AIR_1, CRAZY_AIR_2});
		final List<Flight> flights = ((CrazyAirServiceImpl)supplierService).getFlights(response);
		assertThat(flights.size(), is(2));
		assertThat(flights.get(0).getDepartureAirportCode(), is(IATA.AMSTERDAM_AIRPORT_SCHIPHOL));
		assertThat(flights.get(1).getDepartureDate(), is(LocalDateTime.parse(DEPARTURE_TIME_STR)));
	}
}
