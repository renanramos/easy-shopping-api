package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface AdministratorDelegate {

    AdministratorDTO save(final AdministratorForm administratorForm);

    AdministratorDTO findAdministratorById(final Long administratorId);

    AdministratorDTO updateAdministrator(final AdministratorForm administratorForm, final Long administratorId);

    void removeAdministrator(final Long administratorId);

    PageResponse<AdministratorDTO> findAdministrators(final ParametersRequest parametersRequest, final  String name);
}
