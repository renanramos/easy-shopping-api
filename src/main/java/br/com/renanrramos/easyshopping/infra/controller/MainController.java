/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 22/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author renan.ramos
 *
 */
@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping("/")
	public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
		return new RedirectView("swagger-ui.html");
	}

	
}
