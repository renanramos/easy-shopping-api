package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AddressEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class AddressUseCaseImpl implements AddressUseCase {

    private final AddressGateway addressGateway;

    @Override
    public AddressDTO save(final AddressForm addressForm) {
        final Address address = AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm);
        final AddressEntity newAddressEntity = addressGateway.save(address);
        return AddressMapper.INSTANCE.mapAddressEntityToAddressDTO(newAddressEntity);
    }

    @Override
    public PageResponse<AddressDTO> findAllAddress(Integer pageNumber, Integer pageSize, String sortBy) {
        final Page<AddressEntity> addressEntityPage = addressGateway.findAllAddress(pageNumber, pageSize, sortBy);
        return PageResponse.buildPageResponse(addressEntityPage,
                AddressMapper.INSTANCE.mapAddressEntityListToAddressDTOList(addressEntityPage.getContent()));
    }

    @Override
    public PageResponse<AddressDTO> findAllAddress(final Integer pageNumber, final Integer pageSize,
                                                   final String sortBy, final String streetName) {
        final Page<AddressEntity> addressEntityPage =
                addressGateway.findAllAddress(pageNumber, pageSize, sortBy, streetName);
        return PageResponse.buildPageResponse(addressEntityPage,
                AddressMapper.INSTANCE.mapAddressEntityListToAddressDTOList(addressEntityPage.getContent()));
    }

    @Override
    public AddressDTO findByAddressId(final Long addressId) {
        return AddressMapper.INSTANCE.mapAddressEntityToAddressDTO(
                addressGateway.findAddressById(addressId));
    }

    @Override
    public AddressDTO update(final AddressForm addressForm, final Long addressId) {
        final AddressEntity addressEntity = addressGateway.updateAddress(
                AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm), addressId);
        return AddressMapper.INSTANCE.mapAddressEntityToAddressDTO(addressEntity);
    }

    @Override
    public void removeAddress(final Long addressId) {
        addressGateway.removeAddress(addressId);
    }

}
