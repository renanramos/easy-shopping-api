/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.renanrramos.easyshopping.config.util.JwtTokenUtil;
import br.com.renanrramos.easyshopping.service.jwt.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * @author renan.ramos
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	private static final Logger LOG = LoggerFactory.getLogger(JwtRequestFilter.class);

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private String email;

	private String jwtToken;

	private Integer userId;

	private Map<Object, Object> credentials = new HashMap<>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestTokenHeader = request.getHeader("Authorization");

		LOG.info("RequestHeader token: {}", requestTokenHeader);

		validRequestTokenHeader(requestTokenHeader);

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(email);

			verifyUserProperties(request, userDetails);
		}

		filterChain.doFilter(request, response);
	}

	private void verifyUserProperties(HttpServletRequest request, UserDetails userDetails) {
		try {
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				
				setUserCredentialsProperties();

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, credentials, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		} catch (ExpiredJwtException ex) {
			LOG.info("Error: {}", ex.getMessage());
		}
	}

	private Map<Object, Object> setUserCredentialsProperties() {
		credentials.put("userId", userId);
		credentials.put("userEmail", email);
		return credentials;
	}

	private void validRequestTokenHeader(String requestTokenHeader) {
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

			jwtToken = requestTokenHeader.substring(7);
			try {
				email = jwtTokenUtil.getUsernameFromToken(jwtToken);
				userId = (Integer) jwtTokenUtil.getCurrentUserId(jwtToken);
			} catch(IllegalArgumentException e) {
				LOG.warn("Unable to get JWT token");
			} catch(ExpiredJwtException e) {
				LOG.warn("JWT token has expired");
			}
		} else {
			LOG.warn("JWT token does not begin with Bearer string");
		}
	}
}