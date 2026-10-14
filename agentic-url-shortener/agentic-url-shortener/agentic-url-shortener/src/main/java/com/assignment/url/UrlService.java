package com.assignment.url;

import java.net.URI;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

@Service
public class UrlService {
	private final UrlRepository repo;
	private final ShortCodeGenerator generator;

	public UrlService(UrlRepository repo, ShortCodeGenerator generator) {
		this.repo = repo;
		this.generator = generator;
	}

	public UrlMapping shorten(String url) {
		validate(url);
		String code;
		do {
			code = generator.generate();
		} while (repo.findByShortCode(code).isPresent());
		return repo.save(new UrlMapping(code, url));
	}

	public UrlMapping resolve(String code) {
		UrlMapping m = get(code);
		m.recordClick();
		return m;
	}

	public UrlMapping analytics(String code) {
		return get(code);
	}

	private UrlMapping get(String c) {
		return repo.findByShortCode(c)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Short code not found"));
	}

	private void validate(String u) {
		try {
			URI x = URI.create(u);
			if (x.getHost() == null
					|| !("http".equalsIgnoreCase(x.getScheme()) || "https".equalsIgnoreCase(x.getScheme())))
				throw new Exception();
		} catch (Exception e) {
			throw new ResponseStatusException(BAD_REQUEST, "Only valid http/https URLs are allowed");
		}
	}
}
