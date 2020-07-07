/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.dto.StoreDTO;
import br.com.renanrramos.easyshopping.model.form.StoreForm;
import br.com.renanrramos.easyshopping.service.impl.CompanyService;
import br.com.renanrramos.easyshopping.service.impl.StoreService;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "stores", produces = "application/json")
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private CompanyService companyService;

	private URI uri;
	
	@ResponseBody
	@Transactional
	@PostMapping
	public ResponseEntity<StoreDTO> saveStore(@Valid @RequestBody StoreForm storeForm, UriComponentsBuilder uriBuilder) {
		Optional<Company> company = companyService.findById(storeForm.getCompanyId());
		if (company.isPresent()) {
			Store store = StoreForm.converterStoreFormToStore(storeForm);
			store.setCompany(company.get());
			store = storeService.save(store);
			if (store.getId() != null) {
				uri = uriBuilder.path("/stores/{id}").buildAndExpand(store.getId()).encode().toUri();
				return ResponseEntity.created(uri).body(StoreDTO.converterStoreToStoreDTO(store));
			}
		}	
		return ResponseEntity.badRequest().build();
	}
	
	@ResponseBody
	@GetMapping
	public ResponseEntity<List<StoreDTO>> getStores() {
		List<Store> stores = storeService.findAll();
		return ResponseEntity.ok(StoreDTO.converterStoreListToStoreDTOList(stores));
	}
	
	@ResponseBody
	@GetMapping("/{id}")
	public ResponseEntity<StoreDTO> getStoreById(@PathVariable("id") Long storeId) {
		Optional<Store> storeOptional = storeService.findById(storeId);
		if (storeOptional.isPresent()) {
			StoreDTO storeDto = StoreDTO.converterStoreToStoreDTO(storeOptional.get());
			return ResponseEntity.ok(storeDto);
		}
		return ResponseEntity.notFound().build();
	}
	
	@ResponseBody
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<StoreDTO> updateStore(@PathVariable("id") Long storeId, @RequestBody StoreForm storeForm, UriComponentsBuilder uriBuilder) {
		Optional<Store> storeOptional = storeService.findById(storeId);
		if (storeOptional.isPresent()) {
			Store store = StoreForm.converterStoreFormToStore(storeForm);
			store.setId(storeId);
			StoreDTO storeUpdatedDTO = StoreDTO.converterStoreToStoreDTO(storeService.save(store));
			uri = uriBuilder.path("/stores/{id}").buildAndExpand(storeId).encode().toUri();
			return ResponseEntity.accepted().location(uri).body(storeUpdatedDTO);
		}
		return ResponseEntity.notFound().build();
	}
	
	@ResponseBody
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteStore(@PathVariable("id") Long storeId) {
		Optional<Store> storeOptional = storeService.findById(storeId);
		if (storeOptional.isPresent()) {
			storeService.remove(storeId);
			return ResponseEntity.noContent().build();			
		}
		return ResponseEntity.notFound().build();
	}
}
