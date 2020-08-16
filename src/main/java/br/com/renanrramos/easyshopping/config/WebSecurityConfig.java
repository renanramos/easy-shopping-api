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
					"/api/products/search",
					"/api/products/{id}",
					"/api/products",
					"/api/stores",
					"/api/stores/{id}",
					"/api/product-categories",
					"/api/product-categories/{id}",
					"/api/subcategories",
					"/api/subcategories/{id}",
					"/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/**",
                    "/swagger-ui.html",
                    "/webjars/**").permitAll()
			.antMatchers(HttpMethod.POST, "/api/customers/register").permitAll()
			.antMatchers("/api/customers/**").hasRole(Profile.getProfileName(Profile.CUSTOMER))
			.antMatchers(HttpMethod.POST, "/api/companies/register").permitAll()
			.antMatchers("/api/companies/**").hasRole(Profile.getProfileName(Profile.COMPANY))
			.antMatchers("/api/credit-cards/**").hasRole(Profile.getProfileName(Profile.CUSTOMER))
			.antMatchers("/api/addresses/**").hasRole(Profile.getProfileName(Profile.CUSTOMER))
			.antMatchers("/api/products/**").hasRole(Profile.getProfileName(Profile.COMPANY))
			.antMatchers("/**").hasRole(Profile.getProfileName(Profile.ADMINISTRATOR))
			.and().logout().permitAll()
			.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
