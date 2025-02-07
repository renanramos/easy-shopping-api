package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.ProductImage;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import org.springframework.data.domain.Page;

public interface ProductImageGateway {

    ProductImage save(final ProductImage productImage);

    Page<ProductImage> findAll(final ParametersRequest parametersRequest);

    ProductImage findById(final Long productImageId);

    ProductImage update(final ProductImage productImage, final Long productImageId);

    void remove(final Long productImageId);

    ProductImage getImageByProductId(final Long productId);

}
