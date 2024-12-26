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

import br.com.renanrramos.easyshopping.interfaceadapter.entity.StockItem;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.StockItemRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class StockItemService implements CommonService<StockItem> {

	@Autowired
	private StockItemRepository itemRepository;

	@Override
	public StockItem save(StockItem item) {
		return itemRepository.save(item);
	}

	@Override
	public List<StockItem> findAll(Pageable page) {
		Page<StockItem> pagedResult = itemRepository.findAll(page);
		return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
	}

	@Override
	public Optional<StockItem> findById(Long itemId) {
		return itemRepository.findById(itemId);
	}

	@Override
	public StockItem update(StockItem item) {
		return itemRepository.save(item);
	}

	@Override
	public void remove(Long itemId) {
		itemRepository.deleteById(itemId);
	}

	@Override
	public List<StockItem> findAllPageable(Pageable page, Long id) {
		return new ArrayList<>();
	}

	public List<StockItem> findStockItemByStockId(Pageable page, Long stockId, String name) {
		Page<StockItem> pagedResult = name == null ? itemRepository.findStockItemByStockId(page, stockId)
				: itemRepository.findStockItemByStockIdAndName(page, stockId, name);
		return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
	}

	public Optional<StockItem> findStockItemByProductId(Long productId) {
		return itemRepository.findTopStockItemByProductId(productId);
	}
	
	public boolean hasStockItemByProductId(Long productId) {
		return itemRepository.findTopStockItemByProductId(productId).isPresent();
	}
}
