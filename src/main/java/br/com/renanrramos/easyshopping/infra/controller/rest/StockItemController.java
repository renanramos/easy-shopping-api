/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.domain.constants.PaginationConstantValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.StockItemDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.StockItemForm;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Product;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Stock;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.StockItem;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.StockItemMapper;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import br.com.renanrramos.easyshopping.service.impl.StockItemService;
import br.com.renanrramos.easyshopping.service.impl.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
                                                      UriComponentsBuilder uriBuilder) throws EasyShoppingException {

        Long productId = itemForm.getProductId();

        if (productId == null) {
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
        }

        if (itemService.hasStockItemByProductId(productId)) {
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_ALREADY_IN_STOCK);
        }

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_NOT_FOUND);
        }

        Long stockId = itemForm.getStockId();

        if (stockId == null) {
            throw new EasyShoppingException(ExceptionConstantMessages.STOCK_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<Stock> stockOptional = stockService.findById(stockId);

        if (!stockOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.STOCK_NOT_FOUND);
        }

        if (verifyInvalidStockAmountValues(itemForm)) {
            throw new EasyShoppingException(ExceptionConstantMessages.INVALID_FIELDS_TITLE);
        }

        StockItem item = StockItemMapper.INSTANCE.mapStockItemFormToStockItem(itemForm);
        item.setStock(stockOptional.get());
        item = itemService.save(item);
        item.setProductId(productOptional.get().getId());
        item.setProductName(productOptional.get().getName());
        item.setStock(stockOptional.get());
        uri = uriBuilder.path("/stock-items/{id}").buildAndExpand(item.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(StockItemMapper.INSTANCE.mapStockItemToStockItemDTO(item));
    }

    @ResponseBody
    @PatchMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Update a stock item")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<StockItemDTO> updateStockItem(@PathVariable("id") Long itemId,
                                                        @RequestBody StockItemForm itemForm, UriComponentsBuilder uriBuilder)
            throws EasyShoppingException {
        Long productId = itemForm.getProductId();

        if (productId == null) {
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_NOT_FOUND);
        }

        Long stockId = itemForm.getStockId();

        if (stockId == null) {
            throw new EasyShoppingException(ExceptionConstantMessages.STOCK_ITEM_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<Stock> stockOptional = stockService.findById(stockId);

        if (!stockOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.STOCK_ITEM_NOT_FOUND);
        }

        if (verifyInvalidStockAmountValues(itemForm)) {
            throw new EasyShoppingException(ExceptionConstantMessages.INVALID_FIELDS_TITLE);
        }

        Optional<StockItem> itemOptional = itemService.findById(itemId);

        if (!itemOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.STOCK_ITEM_NOT_FOUND);
        }

        StockItem item = StockItemMapper.INSTANCE.mapStockItemFormToStockItem(itemForm);
        item.setStock(stockOptional.get());
        item.setId(itemId);
        item = itemService.save(item);
        item.setProductId(productOptional.get().getId());
        item.setProductName(productOptional.get().getName());
        item.setStock(stockOptional.get());
        uri = uriBuilder.path("/stock-items/{id}").buildAndExpand(item.getId()).encode().toUri();
        return ResponseEntity.accepted().location(uri).body(StockItemMapper.INSTANCE.mapStockItemToStockItemDTO(item));
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get stock item by id")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<StockItemDTO> getStockItemById(@PathVariable("id") Long itemId) throws EasyShoppingException {

        Optional<StockItem> itemOptional = itemService.findById(itemId);

        if (!itemOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.STOCK_ITEM_NOT_FOUND);
        }

        return ResponseEntity.ok(StockItemMapper.INSTANCE.mapStockItemToStockItemDTO(itemOptional.get()));
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all stock items by stockId")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<List<StockItemDTO>> getStockItems(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) Long stockId,
                                                            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                                            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy)
            throws EasyShoppingException {
        Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        Optional<Stock> stockOptional = stockService.findById(stockId);

        if (!stockOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.STOCK_NOT_FOUND);
        }

        List<StockItem> items = itemService.findStockItemByStockId(page, stockId, name);

        return ResponseEntity.ok(StockItemMapper.INSTANCE.mapStockItemListToStockItemDTOList(items));
    }

    private boolean verifyInvalidStockAmountValues(StockItemForm itemForm) {
        Double maxAmount = itemForm.getMaxAmount();
        Double minAmount = itemForm.getMinAmount();

        return (minAmount == null || minAmount < 0 || minAmount > maxAmount);
    }
}

