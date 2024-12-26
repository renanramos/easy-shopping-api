/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductImage;

/**
 * @author renan.ramos
 *
 */
public interface ProductImageRepository extends PagingAndSortingRepository<ProductImage, Long> {

	Page<ProductImage> findProductImageByProductId(Pageable page, Long productId);
	Optional<ProductImage> findTopProductImageByProductId(Long productId);

	@Transactional
	@Modifying
	@Query("DELETE FROM ProductImage WHERE ID = :productImageId")
	public void removeById(Long productImageId);
}
