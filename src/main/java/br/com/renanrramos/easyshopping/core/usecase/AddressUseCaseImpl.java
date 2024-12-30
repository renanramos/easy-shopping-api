package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class AddressUseCaseImpl implements AddressUseCase {

    private final AddressGateway addressGateway;

    @Override
    public AddressDTO save(final AddressForm addressForm) {
        final Address address = AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm);
        return AddressMapper.INSTANCE.mapAddressToAddressDTO(addressGateway.save(address));
    }

    @Override
    public PageResponse<AddressDTO> findAllAddress(final ParametersRequest parametersRequest) {
        final Page<Address> addressPage = addressGateway.findAllAddress(parametersRequest);
        return PageResponse.buildPageResponse(addressPage,
                AddressMapper.INSTANCE.mapAddressListToAddressDTOList(addressPage.getContent()));
    }

    @Override
    public AddressDTO findByAddressId(final Long addressId) {
        final Address addressById = addressGateway.findAddressById(addressId);
        return AddressMapper.INSTANCE.mapAddressToAddressDTO(addressById);
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

}
