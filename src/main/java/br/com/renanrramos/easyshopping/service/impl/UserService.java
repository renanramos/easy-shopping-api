/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.enums.Profile;

/**
 * @author renan.ramos
 *
 */
@Service
public class UserService {

	private Authentication auth;
	
	public String getCurrentUserId(Principal principal) {
		@SuppressWarnings("unchecked")
		KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
		AccessToken token = keycloakPrincipal.getKeycloakSecurityContext().getToken();
		return token.getId();
	}

	public String getCurrentUserRole() {
		auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().isEmpty()) {
			return "";
		}
		List<GrantedAuthority> authorities = auth.getAuthorities().stream().collect(Collectors.toList());
		return Profile.transformRoleToProfile(authorities.get(0).getAuthority());
	}
}
