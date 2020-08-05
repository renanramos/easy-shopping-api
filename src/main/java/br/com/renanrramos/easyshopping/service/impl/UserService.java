/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.repository.CompanyRepository;
import br.com.renanrramos.easyshopping.repository.CustomerRepository;
import br.com.renanrramos.easyshopping.repository.UserRepository;

/**
 * @author renan.ramos
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CompanyRepository companyReporitory;
	
	public User register(User newUser) {
		User user = userRepository.save(newUser);
		if (user.getProfile().equals(Profile.COMPANY)) {
			companyReporitory.save(setCompanyValues(user));
		} else if (user.getProfile().equals(Profile.CUSTOMER)) {
			customerRepository.save(setCustomerValues(user));
		}		
		return user;
	}

	private Company setCompanyValues(User user) {
		Company company = new Company();
		company.setId(user.getId());
		company.setEmail(user.getEmail());
		company.setPassword(user.getPassword());
		company.setName(user.getName());
		return company;
	}

	private Customer setCustomerValues(User user) {
		Customer customer = new Customer();
		customer.setId(user.getId());
		customer.setName(user.getName());
		customer.setEmail(user.getEmail());
		customer.setPassword(user.getPassword());
		customer.setProfile(user.getProfile());
		return customer;
	}

}
