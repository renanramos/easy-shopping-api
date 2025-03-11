/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author renan.ramos
 *
 */
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long>,
        CrudRepository<ProductEntity, Long> {

    Page<ProductEntity> findProductByStoreId(Pageable page, Long storeId);

    @Query(EasyShoppingSqlConstants.GET_PRODUCT_BY_NAME)
    Page<ProductEntity> getProductByName(Pageable page, @Param("name") String name);

    Page<ProductEntity> findProductByCompanyId(Pageable page, String companyId);

    Page<ProductEntity> findProductBySubCategoryId(Pageable page, Long subCategoryId);

    @Query(EasyShoppingSqlConstants.GET_PRODUCT_BY_NAME_AND_COMPANY_ID)
    Page<ProductEntity> getProductByCompanyIdAndName(Pageable page, @Param("tokenId") String tokenId,
                                                     @Param("name") String name);
}
