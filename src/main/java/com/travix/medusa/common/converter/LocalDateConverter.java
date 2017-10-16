package com.travix.medusa.common.converter;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;

/**
 * Instructs Spring Rest to convert String parameter into {@link LocalDate}.
 *
 * @author islam zenbaei
 *
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

	@Override
	public LocalDate convert(final String date) {
		return LocalDate.parse(date);
	}

}
