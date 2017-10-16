package com.travix.medusa.common.type;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.BaseTest;

public class IATATest extends BaseTest {

	@Test
	public void fromString_whenParamterInLowerCase_thenReturnTheEquivalentEnum() {
		assertThat(IATA.AMSTERDAM_AIRPORT_SCHIPHOL, equalTo(IATA.fromString("ams")));
	}

	@Test
	public void fromString_whenParameterIsNullOrWrong_thenReturnUNKOWN() {
		assertThat(IATA.UNKOWN, equalTo(IATA.fromString(null)));
		assertThat(IATA.UNKOWN, equalTo(IATA.fromString(" ")));
	}

	@Test
	public void deserializingIATA_whenJsonRepresentationIsProvided_thenResolveToEnum() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final IATA iata = mapper.readValue("\"ams\"", IATA.class);
		assertThat(iata, equalTo(IATA.AMSTERDAM_AIRPORT_SCHIPHOL));
	}
}
