package com.twit.configuration;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRETE_KEY = "YmE0bW50YzFqaHY5bTRvbXZvanM3cTF3Y2dwcXViOTN6aWRoZ256NTZkM3R6emhqYzhrMml0ZGdpMndqaXp0d3IzcmtxdjBuNXJjNDdqemhobG81N3BldXptbm9tbDdlOWRsaDMxbzE5dXV1NmNveWx1Yjg1ejMxa2t0cm8xMWE=\r\n";
	
	public <T> T extractClaim(String token,Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClains(token);
		return claimsResolver.apply(claims);
	}
	
	//This method is for Extracting all the Claims
	private Claims extractAllClains(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInkey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private Key getSignInkey() {
		byte[] keyByte = Decoders.BASE64.decode(SECRETE_KEY);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	public String generateToken(UserDetails user) {
		return generateToken(new HashMap<>(),user);
	}
	
	public String generateToken(Map<String, Object> extraClaims,UserDetails user) {
		
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2048))
				.signWith(getSignInkey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
