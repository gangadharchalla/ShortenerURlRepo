package com.assignment.url;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UrlRepository {
	private final Map<String, UrlMapping> data = new ConcurrentHashMap<>();

	public UrlMapping save(UrlMapping m) {
		data.put(m.getShortCode(), m);
		return m;
	}

	public Optional<UrlMapping> findByShortCode(String c) {
		return Optional.ofNullable(data.get(c));
	}
}
