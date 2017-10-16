package com.travix.medusa.supplier.toughJet;

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
import com.travix.medusa.common.type.IATA;
import com.travix.medusa.common.type.Supplier;
import com.travix.medusa.common.util.HttpUtils;
import com.travix.medusa.common.util.MathUtils;
import com.travix.medusa.supplier.SupplierService;

@Service
public class ToughJetServiceImpl implements SupplierService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ToughJetServiceImpl.class);

	final static String
		FROM = "from",
		TO = "to",
		OUTBOUND_DATE = "outboundDate",
		INBOUND_DATE = "inboundDate",
		NUMBER_OF_ADULTS = "numberOfAdults";

	private final RestTemplate restTemplate;

	@Value("${supplier.endpoint.search.toughJet}")
	private String toughJetSearchEndpoint;

	@Value("${supplier.endpoint.search.fakeApi}")
	private boolean fakeApi;

	@Autowired
	public ToughJetServiceImpl(final RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public List<Flight> searchFlights(final FlightSearchDTO flightSearchDTO) {
		final ResponseEntity<ToughJet[]> response = callEndpoint(flightSearchDTO);
		log(response);
		return getFlights(response);
	}

	List<Flight> getFlights(final ResponseEntity<ToughJet[]> response) {
		if (response.getBody() == null) {
			return Collections.emptyList();
		}
		return Arrays.stream(response.getBody()).
				map(tj -> new Flight(tj.getCarrier(), Supplier.TOUGH_JET,
						MathUtils.roundToTwo.apply(calculatePrice(tj.getBasePrice(), tj.getTax(), tj.getDiscount())),
						tj.getDepartureAirportName(), tj.getArrivalAirportName(),
						tj.getOutboundDateTime(), tj.getInboundDateTime())).
				collect(Collectors.toList());
	}

	double calculatePrice(final double basePrice, final double tax, final double discount) {
		LOGGER.debug("Calculate price from base price [{}], tax [{}], discount [{}]",
				basePrice, tax, discount);

		final double discountAmount = basePrice * (discount/100);
		final double price = (basePrice - discountAmount) + tax;
		return price;
	}

	ResponseEntity<ToughJet[]> callEndpoint(final FlightSearchDTO flightSearchDTO) {
		final UriComponents uriComponents = UriComponentsBuilder.fromUriString(toughJetSearchEndpoint).
			queryParam(FROM, flightSearchDTO.getOrigin().getCode()).
			queryParam(TO, flightSearchDTO.getDestination().getCode()).
			queryParam(OUTBOUND_DATE, flightSearchDTO.getDepartureDate()).
			queryParam(INBOUND_DATE, flightSearchDTO.getReturnDate()).
			queryParam(NUMBER_OF_ADULTS, flightSearchDTO.getNumberOfPassengers()).
			build();

		final URI uri = fakeApi ? URI.create(toughJetSearchEndpoint) : uriComponents.toUri();
		LOGGER.debug("Call ToughJet endpoint on [{}]", uri);
		return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(HttpUtils.getHeaders()), ToughJet[].class);
	}

	private void log(final ResponseEntity<ToughJet[]> response) {
		LOGGER.debug("ToughJet search result {}", Arrays.stream(response.getBody())
				.map(tj -> tj.toString())
				.reduce((str1, str2) -> String.join(",", str1, str2))
				.get());
	}

}

/**
 * Internal class that gets populated from Tough Jet search endpoint.
 *
 * @author islam zenbaei
 *
 */
class ToughJet {

    private Airline carrier;

    private double basePrice, tax, discount;

    private IATA departureAirportName, arrivalAirportName;

    private LocalDateTime outboundDateTime, inboundDateTime;

    public ToughJet() {
	}

	public ToughJet(final Airline carrier, final double basePrice, final double tax, final double discount, final IATA departureAirportName,
			final IATA arrivalAirportName, final LocalDateTime outboundDateTime, final LocalDateTime inboundDateTime) {
		super();
		this.carrier = carrier;
		this.basePrice = basePrice;
		this.tax = tax;
		this.discount = discount;
		this.departureAirportName = departureAirportName;
		this.arrivalAirportName = arrivalAirportName;
		this.outboundDateTime = outboundDateTime;
		this.inboundDateTime = inboundDateTime;
	}

	public Airline getCarrier() {
		return carrier;
	}

	public void setCarrier(final Airline carrier) {
		this.carrier = carrier;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(final double basePrice) {
		this.basePrice = basePrice;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(final double tax) {
		this.tax = tax;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(final double discount) {
		this.discount = discount;
	}

	public IATA getDepartureAirportName() {
		return departureAirportName;
	}

	public void setDepartureAirportName(final IATA departureAirportName) {
		this.departureAirportName = departureAirportName;
	}

	public IATA getArrivalAirportName() {
		return arrivalAirportName;
	}

	public void setArrivalAirportName(final IATA arrivalAirportName) {
		this.arrivalAirportName = arrivalAirportName;
	}

	public LocalDateTime getOutboundDateTime() {
		return outboundDateTime;
	}

	public void setOutboundDateTime(final LocalDateTime outboundDateTime) {
		this.outboundDateTime = outboundDateTime;
	}

	public LocalDateTime getInboundDateTime() {
		return inboundDateTime;
	}

	public void setInboundDateTime(final LocalDateTime inboundDateTime) {
		this.inboundDateTime = inboundDateTime;
	}

	@Override
	public String toString() {
		return "ToughJet [carrier=" + carrier + ", basePrice=" + basePrice + ", tax=" + tax + ", discount="
				+ discount + ", departureAirportName=" + departureAirportName + ", arrivalAirportName="
				+ arrivalAirportName + ", outboundDateTime=" + outboundDateTime + ", inboundDateTime=" + inboundDateTime
				+ "]";
	}

}
