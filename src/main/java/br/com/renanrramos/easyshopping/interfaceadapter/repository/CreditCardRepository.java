/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.CreditCardEntity;
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
public interface CreditCardRepository extends PagingAndSortingRepository<CreditCardEntity, Long>,
        CrudRepository<CreditCardEntity, Long> {

    Page<CreditCardEntity> findCreditCardByCustomerId(final Pageable page, final String customerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CreditCardEntity WHERE ID = :creditCardId")
    void removeById(final Long creditCardId);
}
