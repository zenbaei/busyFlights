package com.travix.medusa.common.converter;

import org.springframework.core.convert.converter.Converter;

import com.travix.medusa.common.type.IATA;

/**
 * Instructs Spring Rest to convert String parameter into {@link IATA}.
 *
 * @author islam zenbaei
 *
 */
public class IATAConverter implements Converter<String, IATA> {

	@Override
	public IATA convert(final String code) {
		return IATA.fromString(code);
	}



}
