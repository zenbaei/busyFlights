package com.travix.medusa;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A base class for unit tests that provides a common methods.
 *
 * @author islam zenbaei
 *
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private long testStartDate;
	protected final static DecimalFormat TIME_FORMAT = new DecimalFormat("####,###.###", new DecimalFormatSymbols(Locale.US));
	@Rule
	public TestName name = new TestName();

	public String getAbosluteResourcePathFromClasspath(final String resource) {
		try {
			return URLDecoder.decode(getClass().getResource(resource).getPath(), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Before
	public void setUpBeforeTest() {
		testStartDate = System.currentTimeMillis();
		logger.info("===================================================================");
		logger.info("BEGIN TEST " + name.getMethodName());
		logger.info("===================================================================");

	}

	@After
	public void tearDownAfterTest() {
		final long executionTime = System.currentTimeMillis() - testStartDate;
		logger.info("===================================================================");
		logger.info("END TEST " + name.getMethodName());
		logger.info("execution time: " + TIME_FORMAT.format(executionTime) + " ms");
		logger.info("===================================================================");
	}

}

