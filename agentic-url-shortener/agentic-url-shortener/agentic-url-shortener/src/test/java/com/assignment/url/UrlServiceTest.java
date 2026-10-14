package com.assignment.url;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class UrlServiceTest {
	private UrlService service() {
		return new UrlService(new UrlRepository(), new ShortCodeGenerator());
	}

	@Test
	void shortensValidUrl() {
		assertNotNull(service().shorten("https://example.com").getShortCode());
	}

	@Test
	void rejectsBadScheme() {
		assertThrows(ResponseStatusException.class, () -> service().shorten("ftp://example.com"));
	}

	@Test
	void countsRedirects() {
		UrlService s = service();
		UrlMapping m = s.shorten("https://example.com");
		s.resolve(m.getShortCode());
		s.resolve(m.getShortCode());
		assertEquals(2, s.analytics(m.getShortCode()).getClickCount());
	}
}
