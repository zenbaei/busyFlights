package com.travix.medusa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.travix.medusa.common.converter.IATAConverter;
import com.travix.medusa.common.converter.LocalDateConverter;

/**
 * Customization for Spring web.
 *
 * @author islam zenbaei
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * Allows Rest endpoints to convert String parameters into the expected java Objects.
	 */
	@Override
	public void addFormatters(final FormatterRegistry registry) {
		registry.addConverter(new IATAConverter());
		registry.addConverter(new LocalDateConverter());
	}

}
