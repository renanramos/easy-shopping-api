/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.dto.StockItemDTO;
import br.com.renanrramos.easyshopping.model.form.StockItemForm;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import br.com.renanrramos.easyshopping.service.impl.StockItemService;
import br.com.renanrramos.easyshopping.service.impl.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/stock-items", produces = "application/json")
@Api(tags = "Stock items")
@CrossOrigin(origins = "*")
public class StockItemController {

	@Autowired
	private StockItemService itemService;

	@Autowired
	private ProductService productService;

	@Autowired
	private StockService stockService;

	private URI uri;

	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new stock item")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<StockItemDTO> saveStockItem(@Valid @RequestBody StockItemForm itemForm,
			UriComponentsBuilder uriBuilder, Principal principal) throws EasyShoppingException {

		Long productId = itemForm.getProductId();

		if (productId == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Product> productOptional = productService.findById(productId);

		if (!productOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_NOT_FOUND);
		}

		Long stockId = itemForm.getStockId();

		if (stockId == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.STOCK_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Stock> stockOptional = stockService.findById(stockId);

		if (!stockOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.STOCK_NOT_FOUND);
		}

		verifyInvalidStockAmountValues(itemForm);

		StockItem item = StockItemForm.converterStockItemFormToStockItem(itemForm);
		item.setStock(stockOptional.get());
		item = itemService.save(item);
		uri = uriBuilder.path("/stock-items/{id}").buildAndExpand(item.getId()).encode().toUri();
		return ResponseEntity.created(uri).body(StockItemDTO.converterStockItemToStockItemDTO(item));
	}

	@ResponseBody
	@PatchMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Update a stock item")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<StockItemDTO> updateStockItem(@PathVariable("id") Long itemId,
			@RequestBody StockItemForm itemForm, UriComponentsBuilder uriBuilder, Principal principal)
					throws EasyShoppingException {
		Long productId = itemForm.getProductId();

		if (productId == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Product> productOptional = productService.findById(productId);

		if (!productOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_NOT_FOUND);
		}

		Long stockId = itemForm.getStockId();

		if (stockId == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.STOCK_ITEM_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Stock> stockOptional = stockService.findById(stockId);

		if (!stockOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.STOCK_ITEM_NOT_FOUND);
		}

		if (verifyInvalidStockAmountValues(itemForm)) {
			throw new EasyShoppingException(ExceptionMessagesConstants.INVALID_FIELDS_TITLE);
		}

		Optional<StockItem> itemOptional = itemService.findById(itemId);

		if (!itemOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.STOCK_ITEM_NOT_FOUND);
		}

		StockItem item = StockItemForm.converterStockItemFormUpdateToStockItem(itemForm, itemOptional.get());
		item.setStock(stockOptional.get());
		item = itemService.save(item);
		uri = uriBuilder.path("/stock-items/{id}").buildAndExpand(item.getId()).encode().toUri();
		return ResponseEntity.accepted().location(uri).body(StockItemDTO.converterStockItemToStockItemDTO(item));
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get stock item by id")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<StockItemDTO> getStockItemById(@PathVariable("id") Long itemId) throws EasyShoppingException {

		Optional<StockItem> itemOptional = itemService.findById(itemId);

		if (!itemOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.STOCK_ITEM_NOT_FOUND);
		}

		return ResponseEntity.ok(StockItemDTO.converterStockItemToStockItemDTO(itemOptional.get()));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all stock items by stockId")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<List<StockItemDTO>> getStockItems(@RequestParam(required = false) String name,
			@RequestParam(required = false) Long stockId,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy)
					throws EasyShoppingException {
		Pageable page = new PageableFactory().withPage(pageNumber).withSize(pageSize).withSort(sortBy).buildPageable();

		Optional<Stock> stockOptional = stockService.findById(stockId);

		if (!stockOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.STOCK_NOT_FOUND);
		}

		List<StockItem> items = itemService.findStockItemByStockId(page, stockId);

		return ResponseEntity.ok(StockItemDTO.converterStockItemListToStockItemDTOList(items));
	}

	private boolean verifyInvalidStockAmountValues(StockItemForm itemForm) {
		Double maxAmount = itemForm.getMaxAmount();
		Double minAmount = itemForm.getMinAmount();

		return (minAmount == null || minAmount < 0 || minAmount > maxAmount);
	}
}

