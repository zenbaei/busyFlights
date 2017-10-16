package com.travix.medusa.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.travix.medusa.BaseTest;
import com.travix.medusa.common.util.MathUtils;

public class MathUtilsTest extends BaseTest {

	@Test
	public void roundToTwo_whenHigherThanHald_thenRoundUp() {
		final double d = 21.236;
		assertThat(21.24, is(MathUtils.roundToTwo.apply(d)));
	}

}
