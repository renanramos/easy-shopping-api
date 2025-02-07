/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductImageEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.ProductImageRepository;
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
 */
@Service
public class ProductImageService implements CommonService<ProductImageEntity> {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public ProductImageEntity save(ProductImageEntity productImage) {
        return productImageRepository.save(productImage);
    }

    @Override
    public List<ProductImageEntity> findAll(Pageable page) {
        return productImageRepository.findAll(page).getContent();
    }

    @Override
    public Optional<ProductImageEntity> findById(Long productImageId) {
        return productImageRepository.findById(productImageId);
    }

    @Override
    public ProductImageEntity update(ProductImageEntity productImage) {
        return productImageRepository.save(productImage);
    }

    @Override
    public void remove(Long productImageId) {
        productImageRepository.removeById(productImageId);
    }

    @Override
    public List<ProductImageEntity> findAllPageable(Pageable page, Long id) {
        return productImageRepository.findAll(page).getContent();
    }

    public List<ProductImageEntity> findProductImageByProductId(Pageable page, Long productId) {
        Page<ProductImageEntity> result = productImageRepository.findProductImageByProductId(page, productId);
        return result.hasContent() ?
                result.getContent() :
                new ArrayList<>();
    }

    public Optional<ProductImageEntity> getImageByProductId(Long productId) {
        return Optional.of(productImageRepository.findTopProductImageByProductId(productId));
    }
}
