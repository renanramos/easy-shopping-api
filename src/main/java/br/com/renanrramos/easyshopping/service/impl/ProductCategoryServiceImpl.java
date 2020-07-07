/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.repository.ProductCategoryRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
public class ProductCategoryServiceImpl implements CommonService<ProductCategory>{
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return productCategoryRepository.save(productCategory);
	}

	@Override
	public List<ProductCategory> findAll() {
		return productCategoryRepository.findAll();
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

}
