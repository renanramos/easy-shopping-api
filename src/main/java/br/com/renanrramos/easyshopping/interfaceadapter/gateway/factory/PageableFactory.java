/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 22/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @author renan.ramos
 *
 */
public class PageableFactory {

	Integer pageNumber = 0;
	Integer pageSize = 0;
	String sortBy = "asc";

	public PageableFactory withPageNumber(final Integer pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public PageableFactory withPageSize(final Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public PageableFactory withSortBy(final String sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	public Pageable buildPageable() {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		if (sortBy == null || pageSize <= 0) {
			return pageable;
		}

		if (!sortBy.contains(",")) {
			return PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
		}

		final String[] p = sortBy.split(",");
		final String sortType = p[0];
		final String sortDirection = p[1];

		return (sortDirection.equalsIgnoreCase("asc")) ?
			 PageRequest.of(pageNumber, pageSize, Sort.by(sortType).ascending()) :
			 PageRequest.of(pageNumber, pageSize, Sort.by(sortType).descending());
	}
}
