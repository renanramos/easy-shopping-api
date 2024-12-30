package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface AddressUseCase {

    AddressDTO save(final AddressForm addressForm);

    PageResponse<AddressDTO> findAllAddress(final ParametersRequest parametersRequest);

    AddressDTO findByAddressId(Long addressId);

    AddressDTO update(AddressForm addressForm, Long addressId);

    void removeAddress(Long addressId);
}
