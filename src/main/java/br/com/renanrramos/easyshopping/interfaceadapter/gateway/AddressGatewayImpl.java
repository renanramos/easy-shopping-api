package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AddressRepository;

import br.com.renanrramos.easyshopping.model.AddressEntity;
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

        return buildPageResponse(paginatedAddresses);
    }

    @Override
    public PageResponse<Address> findAllAddress(final Integer pageNumber,
                                                   final Integer pageSize,
                                                   final  String sortBy,
                                                   final String streetName) {
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        final Page<AddressEntity> addressByStreetName =
                addressRepository.findAddressByStreetNameContaining(page, streetName);

        return buildPageResponse(addressByStreetName);
    }

    @Override
    public Address findAddressById(final Long addressId) {
        return addressRepository.findById(addressId)
                .map(AddressMapper.INSTANCE::mapAddressEntityToAddress)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND));
    }

    @Override
    public Address updateAddress(final Address address, final Long addressId) {
        final Optional<AddressEntity> addressById = addressRepository.findById(addressId);
        if (!addressById.isPresent()) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
        }
        final AddressEntity addressEntity = addressById.get();
        AddressMapper.INSTANCE.mapAddressToUpdateAddressEntity(addressEntity, address);
        return AddressMapper.INSTANCE.mapAddressEntityToAddress(addressRepository.save(addressEntity));
    }

    @Override
    public void removeAddress(final Long addressId) {
        final Optional<AddressEntity> addressById = addressRepository.findById(addressId);
        if (!addressById.isPresent()) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
        }
        addressRepository.removeById(addressId);
    }

    private static PageResponse<Address> buildPageResponse(Page<AddressEntity> page) {
        return new PageResponse<>(page.getTotalElements(),
                page.getTotalPages(), AddressMapper.INSTANCE.mapAddressEntityListToAddress(page.getContent()));
    }
}
