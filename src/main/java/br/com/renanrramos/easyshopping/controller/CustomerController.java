/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 25/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.builder.CustomerBuilder;

/**
 * @author renan.ramos
 *
 */
@RestController
public class CustomerController {
	
	@GetMapping("/customers")
	public Customer getCustomers() {
		return new CustomerBuilder()
				.withName("Renan Ramos")
				.withCpf("023101321")
				.withEmail("renanramos@mail.com")
				.build();
	}

}
