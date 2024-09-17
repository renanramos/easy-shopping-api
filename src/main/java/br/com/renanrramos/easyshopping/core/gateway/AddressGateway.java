package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;

public interface AddressGateway {

    Address save(final Address address);

    PageResponse<Address> findAllAddress(final Integer pageNumber,
                                            final Integer pageSize,
                                            final String sortBy);
    PageResponse<Address> findAllAddress(final Integer pageNumber,
                                            final Integer pageSize,
                                            final String sortBy, final String streetName);

    Address findAddressById(final Long addressId);

    Address updateAddress(final Address address, final Long addressId);

    void removeAddress(final Long addressId);
}
