package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.core.domain.Product;
import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.gateway.ProductGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.ProductMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

    private final ProductRepository productRepository;

    @Override
    public Product save(final Product product) {
        final ProductEntity productEntity =
                productRepository.save(ProductMapper.INSTANCE.mapProductToProductEntity(product));
        return ProductMapper.INSTANCE.mapProductEntityToProduct(productEntity);
    }

    @Override
    public Page<Product> findAll(final ParametersRequest parametersRequest) {
        final Page<ProductEntity> productEntityPage =
                productRepository.findAll(new PageableFactory().buildPageable(parametersRequest));
        return getProductPage(productEntityPage);
    }

    @Override
    public Product findById(final Long productId) {
        final ProductEntity productEntity = getProductEntityOrElseThrow(productId);
        return ProductMapper.INSTANCE.mapProductEntityToProduct(productEntity);
    }

    @Override
    public Product update(final Product product, final Long productId) {
        final ProductEntity productEntity = getProductEntityOrElseThrow(productId);
        ProductMapper.INSTANCE.mapProductToUpdateProductEntity(productEntity, product);
        return ProductMapper.INSTANCE.mapProductEntityToProduct(
                productRepository.save(productEntity));
    }

    @Override
    public void remove(final Long productId) {
        getProductEntityOrElseThrow(productId);
        productRepository.deleteById(productId);
    }

    @Override
    public Page<Product> findProductByStoreId(final ParametersRequest parametersRequest, final Long storeId) {
        final Page<ProductEntity> productEntityPage = productRepository.findProductByStoreId(
                new PageableFactory().buildPageable(parametersRequest), storeId);
        return getProductPage(productEntityPage);
    }

    @Override
    public Page<Product> getProductByName(final ParametersRequest parametersRequest, final String name) {
        final Page<ProductEntity> productEntityPage = productRepository.getProductByName(
                new PageableFactory().buildPageable(parametersRequest), name);
        return getProductPage(productEntityPage);
    }

    @Override
    public Page<Product> findProductByCompanyId(final ParametersRequest parametersRequest, final String companyId) {
        final Page<ProductEntity> productEntityPage =
                productRepository.findProductByCompanyId(new PageableFactory().buildPageable(parametersRequest), companyId);
        return getProductPage(productEntityPage);
    }

    @Override
    public Page<Product> findProductBySubCategoryId(final ParametersRequest parametersRequest,
                                                    final Long subCategoryId) {
        final Page<ProductEntity> productEntityPage =
                productRepository.findProductBySubCategoryId(new PageableFactory().buildPageable(parametersRequest), subCategoryId);
        return getProductPage(productEntityPage);
    }

    @Override
    public Page<Product> getProductByCompanyIdAndName(final ParametersRequest parametersRequest,
                                                      final String tokenId,
                                                      final String name) {
        final Page<ProductEntity> productEntityPage =
                productRepository.getProductByCompanyIdAndName(
                        new PageableFactory().buildPageable(parametersRequest), tokenId, name);
        return getProductPage(productEntityPage);
    }

    private ProductEntity getProductEntityOrElseThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_NOT_FOUND));
    }

    private static PageImpl<Product> getProductPage(final Page<ProductEntity> productEntityPage) {
        return new PageImpl<>(ProductMapper.INSTANCE.mapProductEntityListToProductList(productEntityPage.getContent()),
                productEntityPage.getPageable(), productEntityPage.getTotalElements());
    }
}
