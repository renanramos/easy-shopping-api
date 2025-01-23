package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import org.springframework.data.domain.Page;

public interface AdministratorGateway {

    Administrator save(final Administrator administrator);

    Page<Administrator> findAllAdministrators(final ParametersRequest parametersRequest);

    Administrator findAdministratorById(final Long administratorId);

    Administrator updateAdministrator(final Administrator administrator, final Long administratorId);

    void removeAdministrator(final Long administratorId);

    Page<Administrator> searchAdministratorByName(final ParametersRequest parametersRequest, final String name);
}
