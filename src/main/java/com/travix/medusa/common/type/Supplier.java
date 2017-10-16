package com.travix.medusa.common.type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum to hold flight service suppliers' name.
 *
 * @author islam zenbaei
 *
 */
public enum Supplier {

	CRAZY_AIR("CrazyAir"),
	TOUGH_JET("ToughJet"),
	UNKOWN("");

	private static final Map<String, Supplier> names = new HashMap<>();
	private String name;

	static {
		Arrays.stream(Supplier.values()).forEach(type ->
			names.put(type.getName().toUpperCase(), type));
	}

	private Supplier(final String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static Supplier fromString(final String name) {
		return names.getOrDefault(StringUtils.defaultString(name).toUpperCase(),
				Supplier.UNKOWN);
	}
}
