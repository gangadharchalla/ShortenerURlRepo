package com.assignment.url;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class ShortCodeGenerator {
	private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789";
	private final SecureRandom random = new SecureRandom();

	public String generate() {
		var b = new StringBuilder(7);
		for (int i = 0; i < 7; i++)
			b.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
		return b.toString();
	}
}
