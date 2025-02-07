package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.core.domain.ProductImage;
import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.gateway.ProductImageGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductImageEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.ProductImageMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
public class ProductImageGatewayImpl implements ProductImageGateway {

    private final ProductImageRepository productImageRepository;

    @Override
    public ProductImage save(final ProductImage productImage) {
        final ProductImageEntity productImageEntity =
                ProductImageMapper.INSTANCE.mapProductImageToProductImageEntity(productImage);
        final ProductImageEntity newProductImageEntity =
                productImageRepository.save(productImageEntity);
        return ProductImageMapper.INSTANCE.mapProductImageEntityToProductImage(newProductImageEntity);
    }

    @Override
    public Page<ProductImage> findAll(final ParametersRequest parametersRequest) {
        final Page<ProductImageEntity> productImageEntityPage =
                productImageRepository.findAll(new PageableFactory().buildPageable(parametersRequest));
        return new PageImpl<>(ProductImageMapper
                .INSTANCE.mapProductImageEntityListToProductImageList(productImageEntityPage.getContent()),
                productImageEntityPage.getPageable(),
                productImageEntityPage.getTotalElements());
    }

    @Override
    public ProductImage findById(final Long productImageId) {
        final ProductImageEntity productImageEntity = getProductImageEntityOrThrows(productImageId);
        return ProductImageMapper.INSTANCE.mapProductImageEntityToProductImage(productImageEntity);
    }

    @Override
    public ProductImage update(final ProductImage productImage, final Long productImageId) {
        final ProductImageEntity productImageEntity =
                getProductImageEntityOrThrows(productImageId);
        ProductImageMapper.INSTANCE.mapProductImageToUpdateProductImageEntity(productImageEntity, productImage);
        return ProductImageMapper.INSTANCE.mapProductImageEntityToProductImage(productImageEntity);
    }

    @Override
    public void remove(final Long productImageId) {
        getProductImageEntityOrThrows(productImageId);
        productImageRepository.removeById(productImageId);
    }

    @Override
    public ProductImage getImageByProductId(final Long productId) {
        final ProductImageEntity productImageEntity =
                productImageRepository.findTopProductImageByProductId(productId);
        return ProductImageMapper.INSTANCE.mapProductImageEntityToProductImage(productImageEntity);
    }

    private ProductImageEntity getProductImageEntityOrThrows(final Long productImageId) {
        return productImageRepository.findById(productImageId)
                .orElseThrow(() ->
                        new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_IMAGE_NOT_FOUND));
    }

}
