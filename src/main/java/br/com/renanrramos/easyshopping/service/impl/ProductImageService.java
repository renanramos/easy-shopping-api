/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
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

import br.com.renanrramos.easyshopping.model.ProductImage;
import br.com.renanrramos.easyshopping.repository.ProductImageRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class ProductImageService implements CommonService<ProductImage>{

	@Autowired
	private ProductImageRepository productImageRepository;
	
	@Override
	public ProductImage save(ProductImage productImage) {
		return productImageRepository.save(productImage);
	}

	@Override
	public List<ProductImage> findAll(Pageable page) {
		return productImageRepository.findAll(page).getContent();
	}

	@Override
	public Optional<ProductImage> findById(Long productImageId) {
		return productImageRepository.findById(productImageId);
	}

	@Override
	public ProductImage update(ProductImage productImage) {
		return productImageRepository.save(productImage);
	}

	@Override
	public void remove(Long productImageId) {
		productImageRepository.deleteById(productImageId);
	}

	@Override
	public List<ProductImage> findAllPageable(Pageable page, Long id) {
		return productImageRepository.findAll(page).getContent();
	}

	public List<ProductImage> findProductImageByProductId(Pageable page, Long productId) {
		Page<ProductImage> result = productImageRepository.findProductImageByProductId(page, productId);
		return result.hasContent() ?
				result.getContent() :
					new ArrayList<>();
	}
}
