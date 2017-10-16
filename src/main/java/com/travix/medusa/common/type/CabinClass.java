package com.travix.medusa.common.type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum to hold cabin class.
 *
 * @author islam zenbaei
 *
 */
public enum CabinClass {
	ECONOMY("E"),
	BUSINESS("B"),
	UNKOWN("");

	private static final Map<String, CabinClass> names = new HashMap<>();
	private String name;

	static {
		Arrays.stream(CabinClass.values()).forEach(type ->
			names.put(type.getName().toUpperCase(), type));
	}

	private CabinClass(final String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static CabinClass fromString(final String name) {
		return names.getOrDefault(StringUtils.defaultString(name).toUpperCase(),
				CabinClass.UNKOWN);
	}
}
