/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.renanrramos.easyshopping.enums.Profile;

/**
 * @author renan.ramos
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

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
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers(
						"/",
						"/users/authentication",
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
				.antMatchers(HttpMethod.POST, API_CUSTOMERS + REGISTER).permitAll()
				.antMatchers(HttpMethod.POST, API_COMPANIES + REGISTER).permitAll()
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
				.antMatchers(HttpMethod.DELETE, API_CUSTOMERS).hasAnyRole(Profile.getProfileName(Profile.CUSTOMER), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT, API_CUSTOMERS).hasAnyRole(Profile.getProfileName(Profile.CUSTOMER), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT, API_COMPANIES + ID).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(HttpMethod.DELETE, API_COMPANIES).hasAnyRole(Profile.getProfileName(Profile.COMPANY), Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(API_ADMIN).hasRole(Profile.getProfileName(Profile.ADMINISTRATOR))
				.antMatchers(API_ADMIN + ID).hasRole(Profile.getProfileName(Profile.ADMINISTRATOR))
			.and().logout().permitAll()
			.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
