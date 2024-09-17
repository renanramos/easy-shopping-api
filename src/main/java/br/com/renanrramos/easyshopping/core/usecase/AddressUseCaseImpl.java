package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressUseCaseImpl implements AddressUseCase{

    private final AddressGateway addressGateway;

    @Override
    public AddressDTO save(final AddressForm addressForm) {
        final Address address = AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm);
        return AddressMapper.INSTANCE.mapAddressToAddressDTO(addressGateway.save(address));
    }

    @Override
    public PageResponse<AddressDTO> findAllAddress(Integer pageNumber, Integer pageSize, String sortBy) {
        return buildPageResponse(addressGateway.findAllAddress(pageNumber, pageSize, sortBy));
    }

    @Override
    public PageResponse<AddressDTO> findAllAddress(final Integer pageNumber, final Integer pageSize,
                                                   final String sortBy, final String streetName) {
        return buildPageResponse(addressGateway.findAllAddress(pageNumber, pageSize, sortBy, streetName));
    }

    @Override
    public AddressDTO findByAddressId(final Long addressId) {
        return AddressMapper.INSTANCE.mapAddressToAddressDTO(
                addressGateway.findAddressById(addressId));
    }

    @Override
    public AddressDTO update(final AddressForm addressForm, final Long addressId) {
        final Address address = addressGateway.updateAddress(
                AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm), addressId);
        return AddressMapper.INSTANCE.mapAddressToAddressDTO(address);
    }

    @Override
    public void removeAddress(final Long addressId) {
        addressGateway.removeAddress(addressId);
    }

    private PageResponse<AddressDTO> buildPageResponse(PageResponse<Address> pageResponse) {
        return new PageResponse<>(pageResponse.getTotalElements(),
                pageResponse.getTotalPages(),
                AddressMapper.INSTANCE.mapAddressListTOAddressDTOList(pageResponse.getResponseItems()));
    }
}
