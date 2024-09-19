package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.AdministratorUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class AdministratorDelegateImpl implements AdministratorDelegate{

    private final AdministratorUseCase administratorUseCase;

    @Override
    public AdministratorDTO save(final AdministratorForm administratorForm) {
        return administratorUseCase.save(administratorForm);
    }

    @Override
    public AdministratorDTO findAdministratorById(final Long administratorId) {
        return administratorUseCase.findAdministratorById(administratorId);
    }

    @Override
    public AdministratorDTO updateAdministrator(final AdministratorForm administratorForm, final Long administratorId) {
        return administratorUseCase.updateAdministrator(administratorForm, administratorId);
    }

    @Override
    public void removeAdministrator(final Long administratorId) {
        administratorUseCase.removeAdministrator(administratorId);
    }

    @Override
    public PageResponse<AdministratorDTO> findAdministrators(final ParametersRequest parametersRequest, final String name) {
        return StringUtils.isEmpty(name) ?
                findAllAdministrators(parametersRequest) :
                searchAdministratorByName(parametersRequest, name);

    }

    private PageResponse<AdministratorDTO> findAllAdministrators(final ParametersRequest parametersRequest) {
        return administratorUseCase.findAllAdministrators(parametersRequest.getPageNumber(),
                parametersRequest.getPageSize(), parametersRequest.getSortBy());
    }

    private PageResponse<AdministratorDTO> searchAdministratorByName(final ParametersRequest parametersRequest,
                                                                     final String name) {
        return administratorUseCase.searchAdministratorByName(parametersRequest, name);
    }
}
