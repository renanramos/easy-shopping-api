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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

	private URI uri;
	
	private static final String URI_STORE = "/store/{id}";

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private CompanyService companyService;

	@ResponseBody
	@Transactional
	@PostMapping
	public ResponseEntity<StoreDTO> saveStore(@Valid @RequestBody StoreForm storeForm) {
		Optional<Company> company = companyService.findById(storeForm.getCompanyId());
		if (company.isPresent()) {
			Store store = StoreForm.converterStoreFormToStore(storeForm);
			store.setCompany(company.get());
			store = storeService.save(store);
			if (store.getId() != null) {
				ResponseEntity.ok(StoreDTO.converterStoreToStoreDTO(store));
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
	
}
