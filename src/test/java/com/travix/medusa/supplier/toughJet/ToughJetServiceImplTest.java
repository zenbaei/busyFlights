package com.travix.medusa.supplier.toughJet;

import static com.travix.medusa.helper.FlightSearchTestHelper.ARRIVAL_DATE_STR;
import static com.travix.medusa.helper.FlightSearchTestHelper.ARRIVAL_TIME_STR;
import static com.travix.medusa.helper.FlightSearchTestHelper.DEPARTURE_DATE_STR;
import static com.travix.medusa.helper.FlightSearchTestHelper.DEPARTURE_TIME_STR;
import static com.travix.medusa.helper.FlightSearchTestHelper.FLIGHT_SEARCH_DTO;
import static com.travix.medusa.supplier.toughJet.ToughJetServiceImpl.FROM;
import static com.travix.medusa.supplier.toughJet.ToughJetServiceImpl.INBOUND_DATE;
import static com.travix.medusa.supplier.toughJet.ToughJetServiceImpl.NUMBER_OF_ADULTS;
import static com.travix.medusa.supplier.toughJet.ToughJetServiceImpl.OUTBOUND_DATE;
import static com.travix.medusa.supplier.toughJet.ToughJetServiceImpl.TO;
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
import com.travix.medusa.common.type.IATA;
import com.travix.medusa.supplier.SupplierService;


@RestClientTest(ToughJetServiceImpl.class)
public class ToughJetServiceImplTest extends SpringBaseTest {

	private static final ToughJet TOUGH_JET_1 = new ToughJet(Airline.KLM, 300.22, 10.40, 10,
			IATA.AMSTERDAM_AIRPORT_SCHIPHOL, IATA.HEATHROW_AIRPORT, LocalDateTime.parse(DEPARTURE_TIME_STR), LocalDateTime.parse(ARRIVAL_TIME_STR));

	private static final ToughJet TOUGH_JET_2 = new ToughJet(Airline.LUFTHANSA, 250, 20.25, 5,
			IATA.AMSTERDAM_AIRPORT_SCHIPHOL, IATA.HEATHROW_AIRPORT, LocalDateTime.parse(DEPARTURE_TIME_STR), LocalDateTime.parse(ARRIVAL_TIME_STR));

	private String expectedURL;

	@Autowired
	@Qualifier("toughJetServiceImpl")
	private SupplierService supplierService;

	@Value("${supplier.endpoint.search.toughJet}")
	private String toughJetEndpoint;

	@Autowired
    private MockRestServiceServer server;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void service_shouldBeInjected() {
		assertNotNull(supplierService);
		assertTrue(StringUtils.isNotBlank(toughJetEndpoint));
	}

	@Before
	public void setup() {
		expectedURL = toughJetEndpoint + StringUtils.join("?", FROM, "=AMS&", TO,
				"=LHR&", OUTBOUND_DATE, "=", DEPARTURE_DATE_STR, "&", INBOUND_DATE, "=", ARRIVAL_DATE_STR,
				"&", NUMBER_OF_ADULTS, "=1");
	}

	@Test
	public void callEndpoint_whenFlightSearchDTOIsProvided_thenConstructQueryParametersFromIt() throws JsonProcessingException {
		server.expect(requestTo(expectedURL))
			.andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

		((ToughJetServiceImpl)supplierService).callEndpoint(FLIGHT_SEARCH_DTO);
	}

	@Test
	public void callEndpoint_whenSupplierSearchReturnResult_thenItConvertIntoToughJetArray() throws JsonProcessingException {
		final String toughJetJsonArr = objectMapper.writeValueAsString(Arrays.asList(TOUGH_JET_1, TOUGH_JET_2));

		server.expect(requestTo(expectedURL))
			.andRespond(withSuccess(toughJetJsonArr, MediaType.APPLICATION_JSON));

		final ResponseEntity<ToughJet[]> response = ((ToughJetServiceImpl)supplierService).callEndpoint(FLIGHT_SEARCH_DTO);
		assertThat(response.getBody().length, is(2));
		assertThat(response.getBody()[0].getCarrier(), is(Airline.KLM));
		assertThat(response.getBody()[0].getTax(), is(10.40));
		assertThat(response.getBody()[1].getInboundDateTime(), is(LocalDateTime.parse(ARRIVAL_TIME_STR)));
	}

	@Test(expected = RestClientException.class)
	public void callEndpoint_whenHttpCallFails_thenThrowRestClinetException() throws JsonProcessingException {
		server.expect(requestTo(expectedURL))
			.andRespond(withServerError());

		((ToughJetServiceImpl)supplierService).callEndpoint(FLIGHT_SEARCH_DTO);
	}

	@Test
	public void calculatePrice_whenCalled_thenRoundPriceInto2Decimals() {
		final double basePrice = 10.215, tax = 11.2, discount = 20;
		assertThat(((ToughJetServiceImpl)supplierService).calculatePrice(basePrice, tax, discount), is(19.372));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getFlights_whenToughJetArrayIsProvided_thenConvertIntoListOfFlight() {
		final ResponseEntity<ToughJet[]> response = mock(ResponseEntity.class);
		when(response.getBody()).thenReturn(new ToughJet[]{TOUGH_JET_1, TOUGH_JET_2});
		final List<Flight> flights = ((ToughJetServiceImpl)supplierService).getFlights(response);
		assertThat(flights.size(), is(2));
		assertThat(flights.get(0).getDestinationAirportCode(), is(IATA.HEATHROW_AIRPORT));
		assertThat(flights.get(1).getArrivalDate(), is(LocalDateTime.parse(ARRIVAL_TIME_STR)));
	}

	@Test
	public void getFlights_whenEmptyResponse_thenReturnEmptyList() {
		final ResponseEntity<ToughJet[]> response = mock(ResponseEntity.class);
		when(response.getBody()).thenReturn(null);
		final List<Flight> flights = ((ToughJetServiceImpl)supplierService).getFlights(response);
		assertThat(flights.size(), is(0));
	}
}
