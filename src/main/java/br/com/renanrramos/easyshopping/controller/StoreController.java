/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
		Long companyId = storeForm.getCompany().getId();
		if (companyService.isValidCompanyId(companyId)) {
			Store store = StoreForm.converterStoreFormToStore(storeForm);
			store = storeService.save(store);
			if (store.getId() != null) {
				ResponseEntity.ok(StoreDTO.converterStoreToStoreDTO(store));
			}		
		}		
		return ResponseEntity.badRequest().build();
	}
	
}
