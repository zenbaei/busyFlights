package com.travix.medusa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * Annotation for test that requires application context initialization.
 *
 * @author islam zenbaei
 *
 * @see SpringBootTest
 */
@SpringBootTest(classes = BusyFlightsApplication.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringIntegrationTest {

}