/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 10/10/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author renan.ramos
 *
 */
@Configuration
@Profile("development")
public class DevCorsConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
	}
}
