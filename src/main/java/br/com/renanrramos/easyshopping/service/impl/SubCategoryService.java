/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.SubCategory;
import br.com.renanrramos.easyshopping.repository.SubCategoryRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class SubCategoryService implements CommonService<SubCategory>{

	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@Override
	public SubCategory save(SubCategory subCategory) {
		return subCategoryRepository.save(subCategory);
	}

	@Override
	public List<SubCategory> findAll() {
		return new ArrayList<>();
	}

	@Override
	public Optional<SubCategory> findById(Long subCategoryId) {
		return subCategoryRepository.findById(subCategoryId);
	}

	@Override
	public SubCategory update(SubCategory subCategory) {
		return subCategoryRepository.save(subCategory);
	}

	@Override
	public void remove(Long subCategoryId) {
		subCategoryRepository.deleteById(subCategoryId);
	}

	@Override
	public List<SubCategory> findAllPageable(Integer pageNumber, Integer pageSize, String sort) {
		Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sort));
		Page<SubCategory> pagedResult = subCategoryRepository.findAll(page); 
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

}
