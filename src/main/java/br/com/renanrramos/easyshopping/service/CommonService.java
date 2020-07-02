/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service;

import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
 *
 */
public interface CommonService<T> {

	public T save(T entity);
	
	public List<T> findAll();
	
	public Optional<T> findById(Long entityId);
	
	public T update(T entity);
	
	public void remove(Long entityId);
}
