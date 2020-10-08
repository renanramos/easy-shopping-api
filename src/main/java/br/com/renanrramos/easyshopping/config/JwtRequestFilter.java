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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

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

	public static final String COOKIE_NAME = "XSRF-TOKEN";

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private String email;

	private String jwtToken;

	private Integer userId;

	private Map<Object, Object> credentials = new HashMap<>();

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		String requestTokenHeader = request.getHeader("Authorization");
//
//		LOG.info("RequestHeader token: {}", requestTokenHeader);
//
//		setResponseHeaders(request, response);
//		
//		validRequestTokenHeader(requestTokenHeader);
//
//		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(email);
//
//			verifyUserProperties(request, userDetails);
//		}
//
//		filterChain.doFilter(request, response);
//	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		CsrfToken crsf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		String requestTokenHeader = request.getHeader("Authorization");
		setResponseHeaders(request, response);
//		validRequestTokenHeader(requestTokenHeader);
		if (requestTokenHeader != null) {
			Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
			String token = requestTokenHeader.substring(7);//crsf.getToken();

			if (cookie == null || token != null && !token.equals(cookie.getValue()))
            {
                cookie = new Cookie(COOKIE_NAME, token);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
		}
		filterChain.doFilter(request, response);
	}

	private HttpServletResponse setResponseHeaders(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    response.setHeader("Access-Control-Allow-Methods", "POST, PATCH, PUT, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "*");
		return response;
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
		if (requestTokenHeader != null && !requestTokenHeader.substring(4, requestTokenHeader.length()).isEmpty()) {

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