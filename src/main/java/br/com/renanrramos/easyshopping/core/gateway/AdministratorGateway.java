package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AdministratorEntity;
import org.springframework.data.domain.Page;

public interface AdministratorGateway {

    AdministratorEntity save(final Administrator administrator);

    Page<AdministratorEntity> findAllAdministrators(final ParametersRequest parametersRequest);

    AdministratorEntity findAdministratorById(final Long administratorId);

    AdministratorEntity updateAdministrator(final AdministratorForm administratorForm, final Long administratorId);

    void removeAdministrator(final Long administratorId);

    Page<AdministratorEntity> searchAdministratorByName(final ParametersRequest parametersRequest, final String name);
}
