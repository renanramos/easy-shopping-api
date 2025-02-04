/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 25/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.constants.PaginationConstantValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CustomerForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.delegate.CustomerDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

/**
 * @author renan.ramos
 */
@RestController
@RequestMapping(path = "api/customers", produces = "application/json")
@Api(tags = "Customers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {

    private URI uri;

    private final CustomerDelegate customerDelegate;

    @ResponseBody
    @PostMapping
    @Transactional
    @ApiOperation(value = "Save customer information")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody final CustomerForm customerForm,
                                                    final UriComponentsBuilder uriBuilder) {
        final CustomerDTO customerDTO = customerDelegate.save(customerForm);
        uri = uriBuilder.path("/customers/{id}").buildAndExpand(customerDTO.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(customerDTO);
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all customers")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<PageResponse<CustomerDTO>> getCustomers(
            @RequestParam(required = false) final String searchKey,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) final Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) final Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) final String sortBy) {
        return ResponseEntity.ok().body(customerDelegate.findAllPageable(
                new ParametersRequest(pageNumber, pageSize, sortBy), searchKey));
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get a customer by id")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") String tokenId) {
        return ResponseEntity.ok().body(customerDelegate.findByTokenId(tokenId));
    }

    @ResponseBody
    @PatchMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Update a customer")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") final String customerId,
                                                      @RequestBody final CustomerForm customerForm, final UriComponentsBuilder uriBuilder) {
        uri = uriBuilder.path("/customers/{id}").buildAndExpand(customerId).encode().toUri();
        return ResponseEntity.accepted()
                .location(uri)
                .body(customerDelegate.update(customerForm, customerId));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Remove a customer")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<CustomerDTO> removeCustomer(@PathVariable("id") Long customerId) {
        customerDelegate.removeCustomer(customerId);
        return ResponseEntity.ok().build();
    }
}
