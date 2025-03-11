package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.core.domain.ProductCategory;
import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.gateway.ProductCategoryGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductCategoryEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.ProductCategoryMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.ProductCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ProductCategoryGatewayImpl implements ProductCategoryGateway {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory save(final ProductCategory productCategory) {
        final ProductCategoryEntity productCategoryEntity =
                ProductCategoryMapper.INSTANCE.mapProductCategoryToProductCategoryEntity(productCategory);
        final ProductCategoryEntity productCategoryEntityCreated = productCategoryRepository.save(productCategoryEntity);
        return ProductCategoryMapper.INSTANCE.mapProductCategoryEntityToProductCategory(productCategoryEntityCreated);
    }

    @Override
    public Page<ProductCategory> findAll(final ParametersRequest parametersRequest) {
        final Pageable pageable = new PageableFactory().buildPageable(parametersRequest);
        final Page<ProductCategoryEntity> productCategoryEntityPage = productCategoryRepository.findAll(pageable);
        return new PageImpl<>(ProductCategoryMapper
                .INSTANCE.mapProductCategoryEntityListToProductCategoryList(productCategoryEntityPage.getContent()));
    }

    @Override
    public ProductCategory findById(final Long productCategoryId) {
        final ProductCategoryEntity productCategoryEntity = getProductCategoryEntityOrElseThrow(productCategoryId);
        return ProductCategoryMapper.INSTANCE
                .mapProductCategoryEntityToProductCategory(productCategoryEntity);
    }

    @Override
    public ProductCategory update(final ProductCategory productCategory, final Long productCategoryId) {
        final ProductCategoryEntity productCategoryEntity =
                getProductCategoryEntityOrElseThrow(productCategoryId);

        ProductCategoryMapper.INSTANCE.mapProductCategoryToUpdateProductCategoryEntity(productCategoryEntity, productCategory);
        return ProductCategoryMapper.INSTANCE
                .mapProductCategoryEntityToProductCategory(productCategoryRepository.save(productCategoryEntity));
    }

    @Override
    public void remove(final Long productCategoryId) {
        final ProductCategoryEntity productCategoryEntity = getProductCategoryEntityOrElseThrow(productCategoryId);

        productCategoryRepository.delete(productCategoryEntity);
    }

    @Override
    public Page<ProductCategory> findAllProductCategoriesByName(final ParametersRequest parametersRequest, final String name) {
        final Pageable pageable = new PageableFactory().buildPageable(parametersRequest);
        final Page<ProductCategoryEntity> productCategoryPage = productCategoryRepository
                .findProductCategoryByNameContaining(pageable, name);
        return new PageImpl<>(ProductCategoryMapper
                .INSTANCE.mapProductCategoryEntityListToProductCategoryList(productCategoryPage.getContent()));
    }

    private ProductCategoryEntity getProductCategoryEntityOrElseThrow(Long productCategoryId) {
        return productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_CATEGORY_NOT_FOUND));
    }
}
