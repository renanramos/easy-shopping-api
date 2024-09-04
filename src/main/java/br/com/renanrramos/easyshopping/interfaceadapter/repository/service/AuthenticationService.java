/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository.service;

import org.springframework.security.core.Authentication;

/**
 * @author renan.ramos
 *
 */
public interface AuthenticationService {

	Authentication getAuthentication();
}
