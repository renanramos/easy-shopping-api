package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AddressEntity;
import org.springframework.data.domain.Page;

public interface AddressGateway {

    AddressEntity save(final Address address);

    Page<AddressEntity> findAllAddress(final Integer pageNumber,
                                       final Integer pageSize,
                                       final String sortBy);

    Page<AddressEntity> findAllAddress(final Integer pageNumber,
                                       final Integer pageSize,
                                       final String sortBy, final String streetName);

    AddressEntity findAddressById(final Long addressId);

    AddressEntity updateAddress(final Address address, final Long addressId);

    void removeAddress(final Long addressId);
}
