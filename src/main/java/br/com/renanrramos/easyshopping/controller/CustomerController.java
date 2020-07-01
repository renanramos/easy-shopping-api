/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 25/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.model.form.CustomerForm;
import br.com.renanrramos.easyshopping.repository.CustomerRespository;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "customers")
public class CustomerController {
	
	@Autowired
	private CustomerRespository customerRepository;
	
	@ResponseBody
	@Transactional
	@PostMapping
	public ResponseEntity<CustomerDTO> saveCustomer(@Valid CustomerForm customerForm) {
		Customer customer = customerForm.converterCustomerFormToCustomer(customerForm);
		Customer customerCreated = customerRepository.save(customer);
		if (customerCreated.getId() != null) {
			return ResponseEntity.ok(CustomerDTO.converterToCustomerDTO(customer));
		}
		return ResponseEntity.noContent().build();
	}
	
	@ResponseBody
	@GetMapping
	public ResponseEntity<List<CustomerDTO>> getCustomers() {		
		List<Customer> customers = customerRepository.findAll();
		if (customers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}		
		return ResponseEntity.ok(CustomerDTO.converterCustomerListToCustomerDTOList(customers));
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	public ResponseEntity<CustomerDTO> getCustomerByID(@PathVariable("id") Long customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (customer.isPresent()) {
			System.out.println(CustomerDTO.converterToCustomerDTO(customer.get()).toString());
			return ResponseEntity.ok(CustomerDTO.converterToCustomerDTO(customer.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long customerId, CustomerForm customerForm) {
		Optional<Customer> customerToUpdate = customerRepository.findById(customerId);
		if(customerToUpdate.isPresent()) {			
			Customer customer = customerForm.converterCustomerFormToCustomer(customerForm);
			customer.setId(customerId);
			CustomerDTO customerUpdatedDTO = CustomerDTO.converterToCustomerDTO(customerRepository.save(customer));
			return ResponseEntity.ok().body(customerUpdatedDTO);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@ResponseBody
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> removeCustomer(@PathVariable("id") Long customerId) {
		Optional<Customer> customerToRemove = customerRepository.findById(customerId);
		if (customerToRemove.isPresent()) {
			customerRepository.deleteById(customerId);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}	
}
