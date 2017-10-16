package com.travix.medusa.common.type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum to hold IATA codes.
 *
 * @author islam zenbaei
 *
 */
public enum IATA {

	/** contains sample of IATA codes. All codes should be included. */
	HEATHROW_AIRPORT("LHR"),
	AMSTERDAM_AIRPORT_SCHIPHOL("AMS"),
	CAIRO_AIRPORT("CAI"),
	UNKOWN("");

	private static final Map<String, IATA> codes = new HashMap<>();
	private String code;

	static {
		Arrays.stream(IATA.values()).forEach(type ->
			codes.put(type.getCode().toUpperCase(), type));
	}

	private IATA(final String code) {
		this.code = code;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

	@JsonCreator
	public static IATA fromString(final String code) {
		return codes.getOrDefault(StringUtils.defaultString(code).toUpperCase(),
				IATA.UNKOWN);
	}
}
