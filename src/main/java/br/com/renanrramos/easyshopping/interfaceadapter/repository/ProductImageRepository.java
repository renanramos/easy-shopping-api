/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductImageEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author renan.ramos
 */
public interface ProductImageRepository extends PagingAndSortingRepository<ProductImageEntity, Long>,
        CrudRepository<ProductImageEntity, Long> {

    Page<ProductImageEntity> findProductImageByProductId(Pageable page, Long productId);

    ProductImageEntity findTopProductImageByProductId(Long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductImage WHERE ID = :productImageId")
    void removeById(Long productImageId);
}
