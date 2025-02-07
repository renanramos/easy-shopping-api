/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductCategoryEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.ProductCategoryRepository;
import br.com.renanrramos.easyshopping.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
 *
 */
@Service
public class ProductCategoryService implements CommonService<ProductCategoryEntity> {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategoryEntity save(ProductCategoryEntity productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public List<ProductCategoryEntity> findAllPageable(Pageable page, Long id) {
        Page<ProductCategoryEntity> pagedResult = productCategoryRepository.findAll(page);
        return pagedResult.hasContent() ?
                pagedResult.getContent() :
                new ArrayList<>();
    }

    @Override
    public List<ProductCategoryEntity> findAll(Pageable page) {
        return new ArrayList<>();
    }

    @Override
    public Optional<ProductCategoryEntity> findById(Long productCategoryId) {
        return productCategoryRepository.findById(productCategoryId);
    }

    @Override
    public ProductCategoryEntity update(ProductCategoryEntity productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void remove(Long productCategoryId) {
        productCategoryRepository.deleteById(productCategoryId);
    }

    public List<ProductCategoryEntity> findAllProductCategoriesByName(Pageable page, String name) {
        Page<ProductCategoryEntity> pagedResult = productCategoryRepository.findProductCategoryByNameContaining(page, name);
        return pagedResult.hasContent() ?
                pagedResult.getContent() :
                new ArrayList<>();
    }
}
