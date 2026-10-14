package com.assignment.url;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class UrlMapping {
	private final String shortCode;
	private final String originalUrl;
	private final Instant createdAt;
	private final AtomicLong clickCount = new AtomicLong();

	public UrlMapping(String shortCode, String originalUrl) {
		this.shortCode = shortCode;
		this.originalUrl = originalUrl;
		this.createdAt = Instant.now();
	}

	public String getShortCode() {
		return shortCode;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public long getClickCount() {
		return clickCount.get();
	}

	public long recordClick() {
		return clickCount.incrementAndGet();
	}
}
