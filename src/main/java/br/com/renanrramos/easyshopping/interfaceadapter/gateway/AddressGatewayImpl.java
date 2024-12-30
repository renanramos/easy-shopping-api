package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
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
    public AddressEntity save(final Address address) {
        final AddressEntity addressEntity = AddressMapper.INSTANCE.mapAddressToAddressEntity(address);
        return addressRepository.save(addressEntity);
    }

    @Override
    public Page<AddressEntity> findAllAddress(final Integer pageNumber,
                                              final Integer pageSize, final String sortBy) {
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        return addressRepository.findAll(page);
    }

    @Override
    public Page<AddressEntity> findAllAddress(final Integer pageNumber,
                                              final Integer pageSize,
                                              final String sortBy,
                                              final String streetName) {
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        return addressRepository.findAddressByStreetNameContaining(page, streetName);
    }

    @Override
    public AddressEntity findAddressById(final Long addressId) {
        return getAddressEntityOrThrow(addressId);
    }

    @Override
    public AddressEntity updateAddress(final Address address, final Long addressId) {
        final AddressEntity addressEntity = getAddressEntityOrThrow(addressId);
        return addressRepository.save(addressEntity);
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
