/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;

/**
 * @author renan.ramos
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8320224783431182767L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ExceptionMessagesConstants.UNAUTHENTICATED_USER);
	}
}
