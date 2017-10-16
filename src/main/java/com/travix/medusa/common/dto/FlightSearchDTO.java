package com.travix.medusa.common.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.travix.medusa.common.type.IATA;

/**
 * A class that maps to the flight search parameters.
 *
 * @author islam zenbaei
 *
 */
public class FlightSearchDTO {

	@NotNull
	private IATA origin, destination;

	@NotNull
	private LocalDate departureDate, returnDate;

	@Range(min = 1, max = 4)
	private int numberOfPassengers;

	public FlightSearchDTO() {
	}

	public FlightSearchDTO(final IATA origin, final IATA destination, final LocalDate departureDate, final LocalDate returnDate,
			final int numberOfPassengers) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.departureDate = departureDate;
		this.returnDate = returnDate;
		this.numberOfPassengers = numberOfPassengers;
	}

	public IATA getOrigin() {
		return origin;
	}

	public void setOrigin(final IATA origin) {
		this.origin = origin;
	}

	public IATA getDestination() {
		return destination;
	}

	public void setDestination(final IATA destination) {
		this.destination = destination;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(final LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(final LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(final int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	@Override
	public String toString() {
		return "FlightSearchDTO [origin=" + origin + ", destination=" + destination + ", departureDate=" + departureDate
				+ ", returnDate=" + returnDate + ", numberOfPassengers=" + numberOfPassengers + "]";
	}
}
