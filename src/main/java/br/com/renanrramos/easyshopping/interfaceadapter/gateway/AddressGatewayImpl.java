package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AddressRepository;
import br.com.renanrramos.easyshopping.model.Address;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
public class AddressGatewayImpl implements AddressGateway {

    private final AddressRepository addressRepository;

    @Override
    public AddressDTO save(final AddressForm addressForm) {
        final Address address = AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm);
        return AddressMapper.INSTANCE.mapAddressToAddressDTO(addressRepository.save(address));
    }

    @Override
    public PageResponse<AddressDTO> findAllAddress(final Integer pageNumber,
                                                   final Integer pageSize, final String sortBy) {
        final Pageable page = new PageableFactory()
                    .withPageNumber(pageNumber)
                    .withPageSize(pageSize)
                    .withSortBy(sortBy)
                    .buildPageable();

        final Page<Address> paginatedAddresses = addressRepository.findAll(page);

        return buildPageResponse(paginatedAddresses);
    }

    @Override
    public PageResponse<AddressDTO> findAllAddress(final Integer pageNumber,
                                                   final Integer pageSize,
                                                   final  String sortBy,
                                                   final String streetName) {
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        final Page<Address> addressByStreetName =
                addressRepository.findAddressByStreetNameContaining(page, streetName);

        return buildPageResponse(addressByStreetName);
    }

    @Override
    public AddressDTO findAddressById(final Long addressId) {
        return addressRepository.findById(addressId)
                .map(AddressMapper.INSTANCE::mapAddressToAddressDTO)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND));
    }

    @Override
    public AddressDTO updateAddress(final AddressForm addressForm, final Long addressId) {
        final Optional<Address> addressById = addressRepository.findById(addressId);
        if (!addressById.isPresent()) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
        }
        final Address address = addressById.get();
        AddressMapper.INSTANCE.mapAddressFormToUpdateAddress(address, addressForm);
        return AddressMapper.INSTANCE.mapAddressToAddressDTO(addressRepository.save(address));
    }

    @Override
    public void removeAddress(final Long addressId) {
        final Optional<Address> addressById = addressRepository.findById(addressId);
        if (!addressById.isPresent()) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
        }
        addressRepository.removeById(addressId);
    }

    private static PageResponse<AddressDTO> buildPageResponse(Page<Address> page) {
        return new PageResponse<>(page.getTotalElements(),
                page.getTotalPages(), AddressMapper.INSTANCE.mapAddressListTOAddressDTOList(page.getContent()));
    }
}
