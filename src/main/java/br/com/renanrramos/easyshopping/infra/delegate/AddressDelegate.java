package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;


public interface AddressDelegate {
    AddressDTO saveAddress(final AddressForm addressForm);

    PageResponse<AddressDTO> findAddresses(final Integer pageNumber, final Integer pageSize,
                                              final String sortBy, final String streetName);

    AddressDTO findAddressById(Long addressId);
}
