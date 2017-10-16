package com.travix.medusa.common.type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum to hold airline companies name.
 *
 * @author islam zenbaei
 *
 */
public enum Airline {

	/** sample of airline names, all airlines should be added */
	KLM("KLM"),
	LUFTHANSA("Lufthansa"),
	AIR_FRANCE("Air France"),
	JET_AIRWAYS("Jet Airways"),
	UNKOWN("");

	private static final Map<String, Airline> names = new HashMap<>();
	private String name;

	static {
		Arrays.stream(Airline.values()).forEach(type ->
			names.put(type.getName().toUpperCase(), type));
	}

	private Airline(final String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static Airline fromString(final String name) {
		return names.getOrDefault(StringUtils.defaultString(name).toUpperCase(),
				Airline.UNKOWN);
	}

}
