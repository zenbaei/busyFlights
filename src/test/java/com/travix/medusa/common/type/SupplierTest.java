package com.travix.medusa.common.type;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.BaseTest;
import com.travix.medusa.common.type.Supplier;

public class SupplierTest extends BaseTest {

	@Test
	public void fromString_whenParamterInLowerCase_thenReturnTheEquivalentEnum() {
		assertThat(Supplier.CRAZY_AIR, equalTo(Supplier.fromString("crazyair")));
	}

	@Test
	public void fromString_whenParameterIsNullOrWrong_thenReturnUNKOWN() {
		assertThat(Supplier.UNKOWN, equalTo(Supplier.fromString(null)));
		assertThat(Supplier.UNKOWN, equalTo(Supplier.fromString(" ")));
	}

	@Test
	public void deserializingFlightSupplier_whenJsonRepresentationIsProvided_thenResolveToEnum() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final Supplier supplier = mapper.readValue("\"toughjet\"", Supplier.class);
		assertThat(supplier, equalTo(Supplier.TOUGH_JET));
	}
}
