package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.model.Address;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressUseCaseImpl implements AddressUseCase{

    private final AddressGateway addressGateway;

    @Override
    public AddressDTO save(final AddressForm addressForm) {
        return addressGateway.save(addressForm);
    }

    @Override
    public PageResponse<AddressDTO> findAllAddress(Integer pageNumber, Integer pageSize, String sortBy) {
        return addressGateway.findAllAddress(pageNumber, pageSize, sortBy);
    }

    @Override
    public PageResponse<AddressDTO> findAllAddress(final Integer pageNumber, final Integer pageSize,
                                                   final String sortBy, final String streetName) {

        return (streetName == null || streetName.isEmpty()) ?
                addressGateway.findAllAddress(pageNumber, pageSize, sortBy) :
                addressGateway.findAllAddress(pageNumber, pageSize, sortBy, streetName);
    }

    @Override
    public AddressDTO findByAddressId(final Long addressId) {
        return addressGateway.findAddressById(addressId);
    }

    @Override
    public Address update(AddressForm addressForm) {
        return null;
    }

    @Override
    public void removeAddress(Long addressId) {

    }
}
