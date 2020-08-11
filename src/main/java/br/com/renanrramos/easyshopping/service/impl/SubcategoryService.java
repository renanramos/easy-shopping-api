/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.repository.SubcategoryRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class SubcategoryService implements CommonService<Subcategory>{

	@Autowired
	private SubcategoryRepository subCategoryRepository;
	
	@Override
	public Subcategory save(Subcategory subcategory) {
		return subCategoryRepository.save(subcategory);
	}

	@Override
	public List<Subcategory> findAll(Pageable page) {
		return new ArrayList<>();
	}

	@Override
	public Optional<Subcategory> findById(Long subcategoryId) {
		return subCategoryRepository.findById(subcategoryId);
	}

	@Override
	public Subcategory update(Subcategory subcategory) {
		return subCategoryRepository.save(subcategory);
	}

	@Override
	public void remove(Long subcategoryId) {
		subCategoryRepository.deleteById(subcategoryId);
	}

	@Override
	public List<Subcategory> findAllPageable(Pageable page, Long productCategoryId) {
		Page<Subcategory> pagedResult = 
				(productCategoryId == null) ?
				subCategoryRepository.findAll(page) :
				subCategoryRepository.findSubcategoryByProductCategoryId(page, productCategoryId);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}
}
