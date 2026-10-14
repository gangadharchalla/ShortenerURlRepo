package com.assignment.url;

import java.net.URI;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {
	private final UrlService service;

	public UrlController(UrlService service) {
		this.service = service;
	}

	public record ShortenRequest(String url) {
	}

	@PostMapping("/api/urls")
	public ResponseEntity<Map<String, Object>> shorten(@RequestBody ShortenRequest req) {
		UrlMapping m = service.shorten(req.url());
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("shortCode", m.getShortCode(), "shortUrl",
				"http://localhost:8080/" + m.getShortCode(), "originalUrl", m.getOriginalUrl()));
	}

	@GetMapping("/{code}")
	public ResponseEntity<Void> redirect(@PathVariable String code) {
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(service.resolve(code).getOriginalUrl()))
				.build();
	}

	@GetMapping("/api/urls/{code}/analytics")
	public Map<String, Object> analytics(@PathVariable String code) {
		UrlMapping m = service.analytics(code);
		return Map.of("shortCode", m.getShortCode(), "originalUrl", m.getOriginalUrl(), "clickCount", m.getClickCount(),
				"createdAt", m.getCreatedAt());
	}
}
