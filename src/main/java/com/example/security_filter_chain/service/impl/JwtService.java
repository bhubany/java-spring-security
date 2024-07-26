// package com.example.security_filter_chain.service.impl;

// import java.util.Date;
// import java.util.Objects;
// import java.util.UUID;
// import java.util.function.Function;

// import javax.crypto.SecretKey;

// import org.springframework.stereotype.Service;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;
// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class JwtService {
// private static final String SUBJECT = "Authentication";
// private final TaxExemptAppConfig appConfig;

// public String generateToken(UUID authId, UUID sellerLocationId) {
// Date issuedAt = new Date();
// Date expiration = new Date(issuedAt.getTime() +
// appConfig.getJwt().getExpiresIn() * 1000L);
// return Jwts.builder()
// .subject(SUBJECT)
// .id(authId.toString())
// .claim("sellerLocation", sellerLocationId)
// .issuedAt(issuedAt)
// .expiration(expiration)
// .signWith(getSigningKey())
// .compact();
// }

// public UUID extractAuthId(String token) throws InvalidAuthenticationToken {
// validateOrThrow(token);
// String jti = extractClaim(token, Claims::getId);
// if (Objects.isNull(jti)) {
// throw new InvalidAuthenticationToken("authentication.token.missing.jti",
// token);
// }
// return UUID.fromString(jti);
// }

// public UUID extractSellerLocationId(String token) throws
// InvalidAuthenticationToken {
// validateOrThrow(token);
// Claims claims = extractAllClaims(token);
// String sellerLocationIdString = claims.get("sellerLocation", String.class);

// if (Objects.isNull(sellerLocationIdString)) {
// throw new
// InvalidAuthenticationToken("authentication.token.missing.sellerlocation",
// token);
// }

// return UUID.fromString(sellerLocationIdString);

// }

// private void validateOrThrow(String token) throws InvalidAuthenticationToken
// {
// if (Objects.isNull(token)) {
// throw new InvalidAuthenticationToken("authentication.token.missing", token);
// }

// if (!isTokenValid(token) || isTokenExpired(token)) {
// throw new InvalidAuthenticationToken("authentication.token.invalid", token);
// }
// }

// private SecretKey getSigningKey() {
// byte[] keyBytes = Decoders.BASE64.decode(appConfig.getJwt().getSecret());
// return Keys.hmacShaKeyFor(keyBytes);
// }

// private <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
// {
// final Claims claims = extractAllClaims(token);
// return claimsResolver.apply(claims);
// }

// private Claims extractAllClaims(String token) {
// return Jwts.parser()
// .verifyWith(getSigningKey())
// .build()
// .parseSignedClaims(token).getPayload();
// }

// private Boolean isTokenExpired(String token) {
// return extractClaim(token, Claims::getExpiration).before(new Date());
// }

// private Boolean isTokenValid(String token) {
// return Jwts.parser()
// .verifyWith(getSigningKey())
// .build()
// .parseSignedClaims(token)
// .getPayload()
// .getSubject()
// .equals(SUBJECT);
// }

// }