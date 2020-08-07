/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.repository.AddressRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class AddressService implements CommonService<Address> {

	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public Address save(Address address) {
		return addressRepository.save(address);
	}

	@Override
	public List<Address> findAllPageable(Integer pageNumber, Integer pageSize, String sortBy) {
		Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Address> pagedResult = addressRepository.findAll(page); 
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	@Override
	public Optional<Address> findById(Long addressId) {
		return addressRepository.findById(addressId);
	}

	@Override
	public Address update(Address address) {
		return addressRepository.save(address);
	}

	@Override
	public void remove(Long addressId) {
		addressRepository.deleteById(addressId);
	}

	@Override
	public List<Address> findAll() {
		return new ArrayList<>();
	}

	public List<Address> findByCustomerId(Long customerId) {
		return addressRepository.findAddressByCustomerId(customerId);
	}
}
