/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/07/2020
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

import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.ProductCategoryRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class ProductCategoryService implements CommonService<ProductCategory>{
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return productCategoryRepository.save(productCategory);
	}

	@Override
	public List<ProductCategory> findAllPageable(Pageable page, Long id) {
		Page<ProductCategory> pagedResult = productCategoryRepository.findAll(page); 
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	@Override
	public List<ProductCategory> findAll(Pageable page) {
		return new ArrayList<>();
	}

	@Override
	public Optional<ProductCategory> findById(Long productCategoryId) {
		return productCategoryRepository.findById(productCategoryId);
	}

	@Override
	public ProductCategory update(ProductCategory productCategory) {
		return productCategoryRepository.save(productCategory);
	}

	@Override
	public void remove(Long productCategoryId) {
		productCategoryRepository.deleteById(productCategoryId);
	}

	public List<ProductCategory> findAllProductCategoriesByName(Pageable page, String name) {
		Page<ProductCategory> pagedResult = productCategoryRepository.findProductCategoryByNameContaining(page, name); 
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}
}
