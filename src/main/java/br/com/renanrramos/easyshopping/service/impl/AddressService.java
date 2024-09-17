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

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AddressRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@RequiredArgsConstructor
public class AddressService implements CommonService<Address> {

	private final AddressRepository addressRepository;
	
	@Override
	public Address save(Address address) {
		return addressRepository.save(address);
	}

	@Override
	public List<Address> findAllPageable(Pageable page, Long customerId) {
		Page<Address> pagedResult = addressRepository.findAll(page);

		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	public List<Address> findAllPageable(Pageable page, String customerId) {
		Page<Address> pagedResult = 
				(customerId.isEmpty()) ?
				addressRepository.findAll(page) :
				addressRepository.findAddressByCustomerId(page, customerId);
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
		addressRepository.removeById(addressId);
	}
	
	@Override
	public List<Address> findAll(Pageable page) {
		return new ArrayList<>();
	}

	public List<Address> findAddressByStreetName(Pageable page, String streetName) {
		Page<Address> pagedResult =
				addressRepository.findAddressByStreetNameContaining(page, streetName);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}
}
