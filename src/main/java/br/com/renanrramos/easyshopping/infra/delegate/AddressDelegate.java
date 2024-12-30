package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;


public interface AddressDelegate {
    AddressDTO saveAddress(final AddressForm addressForm);

    PageResponse<AddressDTO> findAddresses(final ParametersRequest parametersRequest);

    AddressDTO findAddressById(final Long addressId);

    AddressDTO updateAddress(final AddressForm addressForm, final Long addressId);

    void removeAddress(final Long addressId);
}
