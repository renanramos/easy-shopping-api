package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Product;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import org.springframework.data.domain.Page;

public interface ProductGateway {

    Product save(final Product product);

    Page<Product> findAll(final ParametersRequest parametersRequest);

    Product findById(final Long productId);

    Product update(final Product product, final Long productId);

    void remove(final Long productId);

    Page<Product> findProductByStoreId(final ParametersRequest parametersRequest, final Long storeId);

    Page<Product> getProductByName(final ParametersRequest parametersRequest, final String name);

    Page<Product> findProductByCompanyId(final ParametersRequest parametersRequest, String companyId);

    Page<Product> findProductBySubCategoryId(final ParametersRequest parametersRequest, Long subCategoryId);

    Page<Product> getProductByCompanyIdAndName(final ParametersRequest parametersRequest, final String tokenId,
                                               final String name);
}
