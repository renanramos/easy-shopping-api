/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.SubCategory;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author renan.ramos
 *
 */
public interface SubCategoryRepository extends PagingAndSortingRepository<SubCategory, Long>, CrudRepository<SubCategory, Long> {
	Page<SubCategory> findSubCategoryByProductCategoryId(Pageable page, Long productCategoryId);

	@Query(EasyShoppingSqlConstants.GET_SUBCATEGORIES_BY_NAME)
	Page<SubCategory> findSubCategoryName(Pageable page, @Param("name")String name);

	@Transactional
	@Modifying
	@Query("DELETE FROM SubCategory WHERE ID = :subCategoryId")
	void removeById(Long subCategoryId);
}
