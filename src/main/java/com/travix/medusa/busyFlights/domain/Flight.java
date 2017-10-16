package com.travix.medusa.busyFlights.domain;

import java.time.LocalDateTime;

import com.travix.medusa.common.type.Airline;
import com.travix.medusa.common.type.IATA;
import com.travix.medusa.common.type.Supplier;

/**
 * BusyFlights Domain object.
 *
 * @author islam zenbaei
 *
 */
public class Flight {

	private Airline airline;

	private Supplier supplier;

	private double fare;

	private IATA departureAirportCode, destinationAirportCode;

	private LocalDateTime departureDate, arrivalDate;

	public Flight(final Airline airline, final Supplier supplier, final double fare, final IATA departureAirportCode,
			final IATA destinationAirportCode, final LocalDateTime departureDate, final LocalDateTime arrivalDate) {
		super();
		this.airline = airline;
		this.supplier = supplier;
		this.fare = fare;
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(final Supplier supplier) {
		this.supplier = supplier;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(final double fare) {
		this.fare = fare;
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
		return "Flight [airline=" + airline + ", supplier=" + supplier + ", fare=" + fare + ", departureAirportCode="
				+ departureAirportCode + ", destinationAirportCode=" + destinationAirportCode + ", departureDate="
				+ departureDate + ", arrivalDate=" + arrivalDate + "]";
	}

}
