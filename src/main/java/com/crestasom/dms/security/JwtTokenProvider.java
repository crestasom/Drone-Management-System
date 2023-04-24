package com.crestasom.dms.security;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.crestasom.dms.model.User;
import com.crestasom.dms.service.UserService;
import com.crestasom.dms.util.ConfigUtility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

	private final String SECRET_KEY;
	private final Duration JWT_TOKEN_VALIDITY;
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	private UserService userService;

	public JwtTokenProvider(ConfigUtility configUtility, UserService userService) {
		this.userService = userService;
		SECRET_KEY = configUtility.getProperty("jwt.secret.key", "NSDevil2020!@#");
		JWT_TOKEN_VALIDITY = Duration.ofMinutes(configUtility.getPropertyAsInt("jwt.token.expiry.in.minutes", 20));
	}

	@PostConstruct
	public void loadConfig() {

	}

	public String extractUserName(String token) {
		try {
			return extractClaim(token, Claims::getSubject);
		} catch (Exception ex) {
			if (ex instanceof ExpiredJwtException || ex instanceof SignatureException) {
				throw ex;
			}
			logger.error("Exception !!", ex);
			return null;
		}
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(User userDetails) {
		Map<String, Object> claims = new HashMap<>();
		String token = createToken(claims, userDetails.getUsername());
		return token;
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY.toMillis()))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

	}

	/**
	 * validates jwt token based in userdetails passed in
	 * 
	 * @param token       String jwt token
	 * @param userDetails User user details user to validate the token
	 * @return int -1 if token is invalid, 0 if token has expired, 1 if token is
	 *         valid and not expired
	 * 
	 */
	public boolean validateToken(String token) {

		final String userName = extractUserName(token);
		if (userName == null) {
			return false;
		}
		Optional<User> user = userService.findByUserName(userName);
		if (user.isEmpty()) {
			return false;
		}

		if (userName.equals(user.get().getUsername()) && !isTokenExpired(token)) {
			return true;
		}
		return false;
	}

	public String resolveToken(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		String authHeader = httpServletRequest.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	public Authentication getAuthentication(String token) {
		// TODO Auto-generated method stub
		final String userName = extractUserName(token);
		Optional<User> user = userService.findByUserName(userName);
		return new UsernamePasswordAuthenticationToken(user.get(), "", user.get().getAuthorities());

	}
}
