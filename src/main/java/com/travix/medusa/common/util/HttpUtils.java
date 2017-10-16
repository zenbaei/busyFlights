package com.travix.medusa.common.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Common methods for HTTP operations.
 *
 * @author islam zenbaei
 *
 */
public class HttpUtils {

	public static HttpHeaders getHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
}
