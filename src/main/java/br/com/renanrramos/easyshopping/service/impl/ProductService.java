/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 08/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.ProductRepository;
import br.com.renanrramos.easyshopping.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
 *
 */
@Service
@Transactional
public class ProductService implements CommonService<ProductEntity> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public List<ProductEntity> findAllPageable(Pageable page, Long storeId) {
        Page<ProductEntity> pagedResult =
                (storeId == null) ?
                        productRepository.findAll(page) :
                        productRepository.findProductByStoreId(page, storeId);
        return pagedResult.hasContent() ?
                pagedResult.getContent() :
                new ArrayList<>();
    }

    public List<ProductEntity> findCompanyOwnProducts(Pageable page, String companyId) {
        Page<ProductEntity> pagedResult = productRepository.findProductByCompanyId(page, companyId);
        return pagedResult.hasContent() ?
                pagedResult.getContent() :
                new ArrayList<>();
    }

    @Override
    public List<ProductEntity> findAll(Pageable page) {
        return new ArrayList<>();
    }

    @Override
    public Optional<ProductEntity> findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public ProductEntity update(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public void remove(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<ProductEntity> searchProductByName(Pageable page, String name, Authentication authentication) {
        Page<ProductEntity> pagedResult = (name == null) ? productRepository.findAll(page)
                : productRepository.getProductByName(page, name);

        return pagedResult.hasContent() ?
                pagedResult.getContent() :
                new ArrayList<>();
    }

    public boolean isThereAnyProductWithSubCategoryId(Long productCategoryId) {
        return productRepository.findProductBySubCategoryId(null, productCategoryId).hasContent();
    }

    public List<ProductEntity> getProductsBySubCategoryId(Pageable page, Long subCategoryId) {
        Page<ProductEntity> pagedResult =
                productRepository.findProductBySubCategoryId(page, subCategoryId);
        return pagedResult.hasContent() ?
                pagedResult.getContent() :
                new ArrayList<>();
    }
}
