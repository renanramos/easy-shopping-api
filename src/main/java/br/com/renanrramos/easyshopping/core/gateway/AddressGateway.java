package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;

public interface AddressGateway {

    AddressDTO save(AddressForm addressForm);

    PageResponse<AddressDTO> findAllAddress(final Integer pageNumber,
                                            final Integer pageSize,
                                            final String sortBy);
    PageResponse<AddressDTO> findAllAddress(final Integer pageNumber,
                                            final Integer pageSize,
                                            final String sortBy, final String streetName);

    AddressDTO findAddressById(final Long addressId);
}
