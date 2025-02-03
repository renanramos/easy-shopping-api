/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service;

import org.springframework.security.core.Authentication;

/**
 * @author renan.ramos
 *
 */
public interface BaseAuthenticationService {

    Authentication getAuthentication();

    String getName();
}
