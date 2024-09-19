package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface AdministratorUseCase {

    AdministratorDTO save(final AdministratorForm administratorForm);

    PageResponse<AdministratorDTO> findAllAdministrators(final Integer pageNumber,
                                                      final Integer pageSize,
                                                      final  String sortBy);

    AdministratorDTO findAdministratorById(final Long administratorId);

    AdministratorDTO updateAdministrator(final AdministratorForm administratorForm, final Long administratorId);

    void removeAdministrator(final Long administratorId);

    PageResponse<AdministratorDTO> searchAdministratorByName(final ParametersRequest parametersRequest,
                                                     final String name);
}
