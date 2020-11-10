/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.repository.StockRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class StockService implements CommonService<Stock> {

	@Autowired
	private StockRepository stockRepository;

	@Override
	public Stock save(Stock stock) {
		return stockRepository.save(stock);
	}

	@Override
	public List<Stock> findAll(Pageable page) {
		Page<Stock> pagedResult = stockRepository.findAll(page);
		return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
	}

	@Override
	public Optional<Stock> findById(Long stockId) {
		return stockRepository.findById(stockId);
	}

	@Override
	public Stock update(Stock stock) {
		return stockRepository.save(stock);
	}

	@Override
	public void remove(Long stockId) {
		stockRepository.deleteById(stockId);
	}

	@Override
	public List<Stock> findAllPageable(Pageable page, Long id) {
		return new ArrayList<>();
	}

	public List<Stock> findStockByName(Pageable page, String name) {
		Page<Stock> pagedResult = stockRepository.findStockByName(name);
		return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
	}
}
