package com.travix.medusa;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootApplication
public class BusyFlightsApplication {

	public static void main(final String[] args) {
		SpringApplication.run(BusyFlightsApplication.class, args);
	}

	/**
	 * Customizes {@link LocalDate} representation when serialized to json into ISO-date 'yyyy-MM-dd'.
	 *
	 * @param builder
	 * @return
	 */
	@Bean
    @Primary
    public ObjectMapper objectMapper(final Jackson2ObjectMapperBuilder builder) {
        final ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
	}


}
