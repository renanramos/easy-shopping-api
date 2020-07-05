/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@EnableSpringDataWebSupport
@RestController
public class EasyShopping {
	public static void main(String[] args) {
		SpringApplication.run(EasyShopping.class, args);
	}
	
	@GetMapping
	public ModelAndView index() {
		return new ModelAndView("redirect:/swagger-ui.html");
	}
}
