package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import org.springframework.data.domain.Page;

public interface AddressGateway {

    Address save(final Address address);

    Page<Address> findAllAddress(final ParametersRequest parametersRequest);

    Address findAddressById(final Long addressId);

    Address updateAddress(final Address address, final Long addressId);

    void removeAddress(final Long addressId);
}
