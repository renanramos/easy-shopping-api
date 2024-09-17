/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

/**
 * @author renan.ramos
 *
 */
public interface CommonService<T> {

	T save(T entity);

	List<T> findAll(Pageable page);

	Optional<T> findById(Long entityId);
	
	T update(T entity);
	
	void remove(Long entityId);

	List<T> findAllPageable(Pageable page, Long id);
}
