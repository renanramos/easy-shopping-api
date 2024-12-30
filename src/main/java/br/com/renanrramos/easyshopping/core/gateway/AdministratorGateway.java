package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface AdministratorGateway {

    Administrator save(final Administrator administrator);

    PageResponse<Administrator> findAllAdministrators(final ParametersRequest parametersRequest);

    Administrator findAdministratorById(final Long administratorId);

    Administrator updateAdministrator(final Administrator administrator, final Long administratorId);

    void removeAdministrator(final Long administratorId);

    PageResponse<Administrator> searchAdministratorByName(final ParametersRequest parametersRequest, final String name);
}
