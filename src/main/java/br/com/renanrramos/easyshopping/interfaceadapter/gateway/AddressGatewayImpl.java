package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AddressEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
public class AddressGatewayImpl implements AddressGateway {

    private final AddressRepository addressRepository;

    @Override
    public Address save(final Address address) {
        final AddressEntity addressEntity = AddressMapper.INSTANCE.mapAddressToAddressEntity(address);
        return AddressMapper.INSTANCE.mapAddressEntityToAddress(addressRepository.save(addressEntity));
    }

    @Override
    public Page<Address> findAllAddress(final ParametersRequest parametersRequest) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        final Page<AddressEntity> addressEntities = addressRepository.findAll(page);
        final List<Address> addresses =
                AddressMapper.INSTANCE.mapAddressEntityListToAddressList(addressEntities.getContent());
        return new PageImpl<>(addresses);
    }

    @Override
    public Address findAddressById(final Long addressId) {
        return AddressMapper.INSTANCE.mapAddressEntityToAddress(getAddressEntityOrThrow(addressId));
    }

    @Override
    public Address updateAddress(final Address address, final Long addressId) {
        final AddressEntity updatedAddressEntity = addressRepository.save(getAddressEntityOrThrow(addressId));
        return AddressMapper.INSTANCE.mapAddressEntityToAddress(updatedAddressEntity);
    }

    @Override
    public void removeAddress(final Long addressId) {
        getAddressEntityOrThrow(addressId);
        addressRepository.removeById(addressId);
    }

    private AddressEntity getAddressEntityOrThrow(final Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND));
    }
}
