package com.api.slcontract.security.jwt;

import com.api.slcontract.exception.CustomException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

		@Value("${security.jwt.token.secret-key:secret}")
		private String secretKey = "secret";

		@Value("${security.jwt.token.expire-length:864000000}")
		private long validityInMilliseconds = 864_000_000; // 10d

		@Autowired
		private UserDetailsService userDetailsService;

		@PostConstruct
		protected void init() {
			secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		}

		public String createToken(String username, List<String> roles) {

			Claims claims = Jwts.claims().setSubject(username);
			claims.put("roles", roles);

			Date now = new Date();
			Date validity = new Date(now.getTime() + validityInMilliseconds);

			return Jwts.builder()
							.setClaims(claims)
							.setIssuedAt(now)
							.setExpiration(validity)
							.signWith(SignatureAlgorithm.HS256, secretKey)
							.compact();
		}

		Authentication getAuthentication(String token) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
			return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		}

		public String getUsername(String token) {
			return this.parseClaims(token).getBody().getSubject();
		}

		public String resolveToken(HttpServletRequest req) {
			String bearerToken = req.getHeader("Authorization");
			if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
				return bearerToken.substring(7);
			}
			return null;
		}

		boolean validateToken(String token) {
			Jws<Claims> claims = this.parseClaims(token);

			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		}

		private Jws<Claims> parseClaims(String token) {
			try {
				return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			} catch (JwtException | IllegalArgumentException e) {
				throw new CustomException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
			}
		}
}
