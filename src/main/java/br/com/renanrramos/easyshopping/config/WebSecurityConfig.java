/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.config;

import java.util.Collections;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.renanrramos.easyshopping.enums.Profile;


/**
 * @author renan.ramos
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfig extends KeycloakWebSecurityConfigurerAdapter{

	private static final String ALL_END_POINTS = "/**";

	private static final String REGISTER = "/register";

	private static final String ID = "/{id}";

	private static final String API_CUSTOMERS = "/api/customers";

	private static final String API_COMPANIES = "/api/companies";

	private static final String API_PRODUCTS = "/api/products";

	private static final String API_STORES = "/api/stores";

	private static final String API_PRODUCT_CATEGORIES = "/api/product-categories";

	private static final String API_ADDRESSES = "/api/addresses";

	private static final String API_CREDIT_CARDS = "/api/credit-cards";

	private static final String API_SUBCATEGORIES = "/api/subcategories";

	private static final String API_ADMIN = "/api/admin";

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new NullAuthenticatedSessionStrategy();
	}

	@Bean
	public KeycloakSpringBootConfigResolver keycloakSpringBootConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers(
						"/",
						"/v2/api-docs",
	                    "/configuration/ui",
	                    "/swagger-resources/**",
	                    "/configuration/**",
	                    "/swagger-ui.html",
	                    "/webjars/**").permitAll()
				.antMatchers(HttpMethod.GET, API_PRODUCTS).permitAll()
				.antMatchers(HttpMethod.GET, API_PRODUCTS + ID).permitAll()
				.antMatchers(HttpMethod.GET, API_PRODUCTS + "/search").permitAll()
				.antMatchers(HttpMethod.GET, API_STORES).permitAll()
				.antMatchers(HttpMethod.GET, API_STORES + ID).permitAll()
				.antMatchers(HttpMethod.GET, API_PRODUCT_CATEGORIES).permitAll()
				.antMatchers(HttpMethod.GET, API_PRODUCT_CATEGORIES + ID).permitAll()
				.antMatchers(HttpMethod.GET, API_SUBCATEGORIES).permitAll()
				.antMatchers(HttpMethod.GET, API_SUBCATEGORIES + ID).permitAll()
				.antMatchers(HttpMethod.GET, API_COMPANIES).permitAll()
				.antMatchers(HttpMethod.GET, API_COMPANIES + ID).permitAll()
				.antMatchers(API_CUSTOMERS + REGISTER).permitAll()
				.antMatchers(API_COMPANIES + REGISTER).permitAll()
				.antMatchers(API_CREDIT_CARDS + ALL_END_POINTS).hasAnyRole(Profile.getProfileName(Profile.CUSTOMER), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(API_ADDRESSES + ALL_END_POINTS).hasAnyRole(Profile.getProfileName(Profile.CUSTOMER), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.GET, API_CUSTOMERS).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.GET, API_CUSTOMERS + ID).hasAnyRole(Profile.getProfileName(Profile.CUSTOMER), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.POST, API_STORES).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.DELETE, API_STORES + ID).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT, API_STORES + ID).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.POST, API_PRODUCTS).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.DELETE, API_PRODUCTS + ID).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT, API_PRODUCTS + ID).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.DELETE, API_CUSTOMERS + ID).hasAnyRole(Profile.getProfileName(Profile.CUSTOMER), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT, API_CUSTOMERS + ID).hasAnyRole(Profile.getProfileName(Profile.CUSTOMER), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT, API_COMPANIES + ID).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.DELETE, API_COMPANIES + ID).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.POST, API_PRODUCT_CATEGORIES).hasAnyRole(Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT, API_PRODUCT_CATEGORIES + ID).hasAnyRole(Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.DELETE, API_PRODUCT_CATEGORIES + ID).hasAnyRole(Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.POST, API_SUBCATEGORIES).hasAnyRole(Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT, API_SUBCATEGORIES + ID).hasAnyRole(Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.DELETE, API_SUBCATEGORIES + ID).hasAnyRole(Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(API_ADMIN).hasRole(Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(API_ADMIN + ID).hasRole(Profile.getProfileName(Profile.ADMINISTRATOR));
	}
}
