/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 22/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.factory;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author renan.ramos
 *
 */
public class PageableFactory {

	private final int page;
	private final int size;
	private final String sort;
	
	public PageableFactory(int page, int size, String sort) {
		this.page = page;
		this.size = size;
		this.sort = sort;
	}
	
	public Pageable getPageable() {
		Pageable pageable = PageRequest.of(page, size);
		
		if (sort != null) {
			if (sort.contains(",")) {
				String p[] = sort.split(",");
				String sortType = p[0];
				String sortDirection = p[1];
				
				if (sortDirection.equals("asc") && !sortDirection.equals("desc")) {
					sortDirection = "asc";
				}
				
				if (sortDirection.equalsIgnoreCase("asc")) {
					pageable = PageRequest.of(page, size, Sort.by(sortType).ascending());
				} else {
					pageable = PageRequest.of(page, size, Sort.by(sortType).descending());
				}
			} else {
				pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
			}
		}
		return pageable;
	}
	
	
}
