package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.AddressUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;

import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressDelegateImpl implements AddressDelegate{

    private final AddressUseCase addressUseCase;

    @Override
    public AddressDTO saveAddress(final AddressForm addressForm) {
        return addressUseCase.save(addressForm);
    }

    @Override
    public PageResponse<AddressDTO> findAddresses(final Integer pageNumber, final Integer pageSize,
                                                     final String sortBy, final String streetName) {
        return addressUseCase.findAllAddress(pageNumber, pageSize, sortBy);
    }

    @Override
    public AddressDTO findAddressById(final Long addressId) {
        return addressUseCase.findByAddressId(addressId);
    }
}
