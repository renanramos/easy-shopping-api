/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.renanrramos.easyshopping.constants.sql.EasyShoppingSqlConstants;
import br.com.renanrramos.easyshopping.model.Subcategory;

/**
 * @author renan.ramos
 *
 */
public interface SubcategoryRepository extends PagingAndSortingRepository<Subcategory, Long> {
	Page<Subcategory> findSubcategoryByProductCategoryId(Pageable page, Long productCategoryId);

	@Query(EasyShoppingSqlConstants.GET_SUBCATEGORIES_BY_NAME)
	Page<Subcategory> findSubcategoryName(Pageable page, @Param("name")String name);

	@Transactional
	@Modifying
	@Query("DELETE FROM Subcategory WHERE ID = :subcategoryId")
	public void removeById(Long subcategoryId);
}
