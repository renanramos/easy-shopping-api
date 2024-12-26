/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import br.com.renanrramos.easyshopping.interfaceadapter.domain.Product;

/**
 * @author renan.ramos
 *
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

	Page<Product> findProductByStoreId(Pageable page, Long storeId);

	@Query(EasyShoppingSqlConstants.GET_PRODUCT_BY_NAME)
	Page<Product> getProductByName(Pageable page, @Param("name") String name);

	Page<Product> findProductByCompanyId(Pageable page, String companyId);

	Page<Product> findProductBySubCategoryId(Pageable page, Long subCategoryId);

	@Query(EasyShoppingSqlConstants.GET_PRODUCT_BY_NAME_AND_COMPANY_ID)
	Page<Product> getProductByCompanyIdAndName(Pageable page, @Param("tokenId") String tokenId,
			@Param("name") String name);
}
