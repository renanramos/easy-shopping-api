package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;

public interface AddressUseCase {

    AddressDTO save(final AddressForm addressForm);

    PageResponse<AddressDTO> findAllAddress(final Integer pageNumber, final Integer pageSize,
                                            final String sortBy);

    PageResponse<AddressDTO> findAllAddress(final Integer pageNumber, final Integer pageSize,
                                            final String sortBy, final String streetName);

    AddressDTO findByAddressId(Long addressId);

    AddressDTO update(AddressForm addressForm, Long addressId);

    void removeAddress(Long addressId);
}
