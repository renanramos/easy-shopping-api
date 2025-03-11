/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.constants.PaginationConstantValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.delegate.AdministratorDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "/api/admin", produces = "application/json")
@Api(tags = "Administrators")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorDelegate administratorDelegate;

    @ResponseBody
    @PostMapping(path = "/register")
    @Transactional
    @ApiOperation(value = "Save a new administrator")
    public ResponseEntity<AdministratorDTO> saveAdministrator(@Valid @RequestBody AdministratorForm administratorForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(administratorDelegate.save(administratorForm));
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all administrators")
    @RolesAllowed({"ADMINISTRATOR", "easy-shopping-admin"})
    public ResponseEntity<PageResponse<AdministratorDTO>> getAdministrators(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(administratorDelegate
                        .findAdministrators(new ParametersRequest(pageNumber, pageSize, sortBy), name));
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get an administrator by id")
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<AdministratorDTO> getAdministratorById(@NotNull @PathVariable("id") Long administratorId) {
        return ResponseEntity.status(HttpStatus.OK).body(administratorDelegate.findAdministratorById(administratorId));
    }

    @ResponseBody
    @PatchMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Update an administrator")
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<AdministratorDTO> updateAdministrator(@NotNull @PathVariable("id") Long administratorId,
                                                                @RequestBody AdministratorForm administratorForm) {
        return ResponseEntity.status(HttpStatus.OK).body(administratorDelegate
                .updateAdministrator(administratorForm, administratorId));
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Remove an administrator")
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<AdministratorDTO> removeAdministrator(@NotNull @PathVariable("id") Long administratorId) {
        administratorDelegate.removeAdministrator(administratorId);
        return ResponseEntity.ok().build();
    }
}