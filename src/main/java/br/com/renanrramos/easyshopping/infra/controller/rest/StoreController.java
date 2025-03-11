/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.domain.constants.PaginationConstantValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.StoreDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.StoreForm;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Store;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.StoreMapper;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationService;
import br.com.renanrramos.easyshopping.service.impl.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
 */
@RestController
@RequestMapping(path = "api/stores", produces = "application/json")
@Api(tags = "Stores")
@CrossOrigin(origins = "*")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private AuthenticationService authenticationServiceImpl;

    private URI uri;

    @ResponseBody
    @Transactional
    @PostMapping
    @ApiOperation(value = "Save a new store")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<StoreDTO> saveStore(@Valid @RequestBody StoreForm storeForm, UriComponentsBuilder uriBuilder)
            throws EasyShoppingException {

        if (storeService.isRegisteredNumberInvalid(storeForm.getRegisteredNumber())) {
            throw new EasyShoppingException(ExceptionConstantMessages.CNPJ_ALREADY_EXIST);
        }

        Store store = StoreMapper.INSTANCE.mapStoreFormToStore(storeForm);
        store.setTokenId(authenticationServiceImpl.getName());
        store = storeService.save(store);
        uri = uriBuilder.path("/stores/{id}").buildAndExpand(store.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(StoreMapper.INSTANCE.mapStoreToStoreDTO(store));
    }

    @ResponseBody
    @GetMapping("/company")
    @ApiOperation(value = "Get all stores of the logged in company")
    public ResponseEntity<List<StoreDTO>> getCompanyOwnStores(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {

        Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        List<Store> stores = storeService.findAllPageable(page, authenticationServiceImpl.getName(), name);
        return ResponseEntity.ok(StoreMapper.INSTANCE.mapStoreListToStoreDTOList(stores));
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all stores")
    public ResponseEntity<List<StoreDTO>> getStores(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {
        Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        List<Store> stores = (name == null || name.isEmpty()) ?
                storeService.findAll(page) :
                storeService.findStoreByName(page, name);
        return ResponseEntity.ok(StoreMapper.INSTANCE.mapStoreListToStoreDTOList(stores));
    }

    @ResponseBody
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a store by id")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable("id") Long storeId) {
        Optional<Store> storeOptional = storeService.findById(storeId);
        if (storeOptional.isPresent()) {
            StoreDTO storeDto = StoreMapper.INSTANCE.mapStoreToStoreDTO(storeOptional.get());
            return ResponseEntity.ok(storeDto);
        }
        return ResponseEntity.notFound().build();
    }

    @ResponseBody
    @PatchMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Update a store")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<StoreDTO> updateStore(@PathVariable("id") Long storeId, @RequestBody StoreForm storeForm,
                                                UriComponentsBuilder uriBuilder) throws EasyShoppingException {

        Optional<Store> currentStore = storeService.findById(storeId);

        if (!currentStore.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.STORE_NOT_FOUND);
        }

        Store store = currentStore.get();
        StoreMapper.INSTANCE.mapStoreFormToUpdateStore(store, storeForm);
        store.setTokenId(authenticationServiceImpl.getName());
        store.setId(storeId);
        StoreDTO storeUpdatedDTO = StoreMapper.INSTANCE.mapStoreToStoreDTO(storeService.update(store));
        uri = uriBuilder.path("/stores/{id}").buildAndExpand(storeId).encode().toUri();

        return ResponseEntity.accepted().location(uri).body(storeUpdatedDTO);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Remove a store")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<StoreDTO> removeStore(@PathVariable("id") Long storeId) {
        Optional<Store> storeOptional = storeService.findById(storeId);
        if (!storeOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.STORE_NOT_FOUND);
        }
        storeService.remove(storeId);
        return ResponseEntity.ok().build();
    }
}
