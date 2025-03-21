package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.AddressUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressDelegateImpl implements AddressDelegate {

    private final AddressUseCase addressUseCase;

    @Override
    public AddressDTO saveAddress(final AddressForm addressForm) {
        return addressUseCase.save(addressForm);
    }

    @Override
    public PageResponse<AddressDTO> findAddresses(final ParametersRequest parametersRequest) {
        return addressUseCase.findAllAddress(parametersRequest);
    }

    @Override
    public AddressDTO findAddressById(final Long addressId) {
        return addressUseCase.findByAddressId(addressId);
    }

    @Override
    public AddressDTO updateAddress(final AddressForm addressForm, final Long addressId) {
        return addressUseCase.update(addressForm, addressId);
    }

    @Override
    public void removeAddress(final Long addressId) {
        addressUseCase.removeAddress(addressId);
    }
}
