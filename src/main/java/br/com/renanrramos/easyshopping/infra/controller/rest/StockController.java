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
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.StockDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.StockForm;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Stock;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Store;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.StockMapper;
import br.com.renanrramos.easyshopping.service.impl.StockService;
import br.com.renanrramos.easyshopping.service.impl.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
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
@RequestMapping(path = "api/stocks", produces = "application/json")
@Api(tags = "Stocks")
@CrossOrigin(origins = "*")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StoreService storeService;

    private URI uri;

    @PostMapping
    @Transactional
    @ApiOperation(value = "Save a new stock")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<StockDTO> saveStock(@Valid @RequestBody StockForm stockForm, UriComponentsBuilder uriBuilder)
            throws EasyShoppingException {

        Long storeId = stockForm.getStoreId();

        if (storeId == null) {
            throw new EasyShoppingException(ExceptionConstantMessages.STORE_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<Store> storeOptional = storeService.findById(storeId);

        if (!storeOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.STORE_NOT_FOUND);
        }

        Stock stock = StockMapper.INSTANCE.mapStockFormToStock(stockForm);
        stock.setStore(storeOptional.get());

        stock = stockService.save(stock);

        uri = uriBuilder.path("/stock/{id}").buildAndExpand(stock.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(StockMapper.INSTANCE.mapStockToStockDTO(stock));
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all stocks")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<List<StockDTO>> getStocks(@RequestParam(required = false) String name,
                                                    @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                                    @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                    @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {

        Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        List<Stock> stocks = (name == null) ? stockService.findAll(page) : stockService.findStockByName(page, name);
        return ResponseEntity.ok(StockMapper.INSTANCE.mapStockListToStockDTOList(stocks));
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get stock by id")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<StockDTO> getStockById(@PathVariable("id") Long stockId) throws EasyShoppingException {

        Optional<Stock> stockOptional = stockService.findById(stockId);

        if (!stockOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.STOCK_NOT_FOUND);
        }

        return ResponseEntity.ok(StockMapper.INSTANCE.mapStockToStockDTO(stockOptional.get()));
    }

    @ResponseBody
    @PatchMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Update a stock")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<StockDTO> updateStock(@PathVariable("id") Long stockId, @RequestBody StockForm stockForm,
                                                UriComponentsBuilder uriBuilder) throws EasyShoppingException {

        Optional<Stock> currentStock = stockService.findById(stockId);

        if (!currentStock.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.STOCK_NOT_FOUND);
        }

        Long storeId = stockForm.getStoreId();

        if (storeId == null) {
            throw new EasyShoppingException(ExceptionConstantMessages.STORE_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<Store> storeOptional = storeService.findById(storeId);

        if (!storeOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.STORE_NOT_FOUND);
        }
        Stock stock = currentStock.get();
        StockMapper.INSTANCE.mapStockFormToUpdateStock(stock, stockForm);
        stock.setId(stockId);
        stock.setStore(storeOptional.get());
        StockDTO stockUpdatedDTO = StockMapper.INSTANCE.mapStockToStockDTO(stockService.update(stock));
        uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stockId).encode().toUri();

        return ResponseEntity.accepted().location(uri).body(stockUpdatedDTO);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Remove a stock")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<StockDTO> removeStock(@PathVariable("id") Long stockId) {
        Optional<Stock> stockOptional = stockService.findById(stockId);
        if (!stockOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.STOCK_NOT_FOUND);
        }
        stockService.remove(stockId);
        return ResponseEntity.ok().build();
    }
}
