package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AddressEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
public class AddressGatewayImpl implements AddressGateway {

    private final AddressRepository addressRepository;

    @Override
    public Address save(final Address address) {
        final AddressEntity addressEntity = AddressMapper.INSTANCE.mapAddressToAddressEntity(address);
        return AddressMapper.INSTANCE.mapAddressEntityToAddress(addressRepository.save(addressEntity));
    }

    @Override
    public PageResponse<Address> findAllAddress(final Integer pageNumber,
                                                final Integer pageSize, final String sortBy) {
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        final Page<AddressEntity> paginatedAddresses = addressRepository.findAll(page);

        return PageResponse.buildPageResponse(paginatedAddresses,
                AddressMapper.INSTANCE.mapAddressEntityListToAddress(paginatedAddresses.getContent()));
    }

    @Override
    public PageResponse<Address> findAllAddress(final Integer pageNumber,
                                                final Integer pageSize,
                                                final String sortBy,
                                                final String streetName) {
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        final Page<AddressEntity> addressByStreetName =
                addressRepository.findAddressByStreetNameContaining(page, streetName);

        return PageResponse.buildPageResponse(addressByStreetName,
                AddressMapper.INSTANCE.mapAddressEntityListToAddress(addressByStreetName.getContent()));
    }

    @Override
    public Address findAddressById(final Long addressId) {
        final AddressEntity addressEntity = getAddressEntityOrThrow(addressId);
        return AddressMapper.INSTANCE.mapAddressEntityToAddress(addressEntity);
    }

    @Override
    public Address updateAddress(final Address address, final Long addressId) {
        final AddressEntity addressEntity = getAddressEntityOrThrow(addressId);
        AddressMapper.INSTANCE.mapAddressToUpdateAddressEntity(addressEntity, address);
        return AddressMapper.INSTANCE.mapAddressEntityToAddress(addressRepository.save(addressEntity));
    }

    @Override
    public void removeAddress(final Long addressId) {
        getAddressEntityOrThrow(addressId);
        addressRepository.removeById(addressId);
    }

    private AddressEntity getAddressEntityOrThrow(final Long addressId) {
        final Optional<AddressEntity> addressById = addressRepository.findById(addressId);
        if (addressById.isEmpty()) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
        }
        return addressById.get();
    }
}
