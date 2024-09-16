/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 22/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest.factory;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
public class PageableFactory {

	private int pageNumber;
	private int pageSize;
	private String sortBy;
	
	public PageableFactory() {
		// Intentionally empty
	}
	
	public PageableFactory(int pageNumber, int pageSize, String sortBy) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
	}

	public PageableFactory withPage(int pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public PageableFactory withSize(int pageSize) {
		this.pageSize = pageSize >= 1 ? pageSize : Integer.MAX_VALUE;
		return this;
	}

	public PageableFactory withSort(String sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	public Pageable buildPageable() {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		if (sortBy != null && pageSize >= 1) {
			if (sortBy.contains(",")) {
				String p[] = sortBy.split(",");
				String sortType = p[0];
				String sortDirection = p[1];
				
				if (sortDirection.equals("asc")) {
					sortDirection = "asc";
				}
				
				if (sortDirection.equalsIgnoreCase("asc")) {
					pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortType).ascending());
				} else {
					pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortType).descending());
				}
			} else {
				pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
			}
		}
		return pageable;
	}
}
