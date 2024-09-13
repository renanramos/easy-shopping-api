/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 08/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.repository.ProductRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
@Transactional
public class ProductService implements CommonService<Product>{

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public List<Product> findAllPageable(Pageable page, Long storeId) {
		Page<Product> pagedResult =
				(storeId == null) ?
						productRepository.findAll(page) :
							productRepository.findProductByStoreId(page, storeId);
						return pagedResult.hasContent() ?
								pagedResult.getContent() :
									new ArrayList<>();
	}

	public List<Product> findCompanyOwnProducts(Pageable page, String companyId) {
		Page<Product> pagedResult = productRepository.findProductByCompanyId(page, companyId);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	@Override
	public List<Product> findAll(Pageable page) {
		return new ArrayList<>();
	}

	@Override
	public Optional<Product> findById(Long productId) {
		return productRepository.findById(productId);
	}

	@Override
	public Product update(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void remove(Long productId) {
		productRepository.deleteById(productId);
	}

	public List<Product> searchProductByName(Pageable page, String name, Authentication authentication) {
		Page<Product> pagedResult = (name == null) ? productRepository.findAll(page)
				: productRepository.getProductByName(page, name);

		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	public boolean isThereAnyProductWithSubCategoryId(Long productCategoryId) {
		return productRepository.findProductBySubCategoryId(null, productCategoryId).hasContent();
	}

	public List<Product> getProductsBySubCategoryId(Pageable page, Long subCategoryId) {
		Page<Product> pagedResult =
				productRepository.findProductBySubCategoryId(page, subCategoryId);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}
}
