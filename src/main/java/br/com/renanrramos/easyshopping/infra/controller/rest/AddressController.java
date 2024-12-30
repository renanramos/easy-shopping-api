/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 12/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.delegate.AddressDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @author renan.ramos
 */
@Controller
@RequestMapping(path = "api/addresses", produces = "application/json")
@Api(tags = "Address")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AddressController {

    private final AddressDelegate addressDelegate;

    @ResponseBody
    @PostMapping
    @Transactional
    @ApiOperation(value = "Save a new address")
    @RolesAllowed({"CUSTOMER", "easy-shopping-user", "app-customer"})
    public ResponseEntity<AddressDTO> saveAddress(@Valid @RequestBody AddressForm addressForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressDelegate.saveAddress(addressForm));
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all addresses")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<PageResponse<AddressDTO>> getAddresses(
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressDelegate.findAddresses(new ParametersRequest(pageNumber, pageSize, sortBy)));
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get an address by id")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") Long addressId) {
        return ResponseEntity.ok(addressDelegate.findAddressById(addressId));
    }

    @ResponseBody
    @PatchMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Update an address")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable("id") Long addressId,
                                                    @RequestBody AddressForm addressForm, UriComponentsBuilder uriBuilder) {
        return ResponseEntity.accepted()
                .location(uriBuilder.path("/addresses/{id}")
                        .buildAndExpand(addressId)
                        .encode()
                        .toUri())
                .body(addressDelegate.updateAddress(addressForm, addressId));
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Remove an address")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<AddressDTO> removeAddress(@PathVariable("id") Long addressId) {
        addressDelegate.removeAddress(addressId);
        return ResponseEntity.ok().build();
    }

}
