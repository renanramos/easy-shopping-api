package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.core.gateway.AdministratorGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AdministratorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class AdministratorUseCaseImpl implements AdministratorUseCase {

    private final AdministratorGateway administratorGateway;

    @Override
    public AdministratorDTO save(final AdministratorForm administratorForm) {
        final Administrator administrator =
                AdministratorMapper.INSTANCE.mapAdministratorFormToAdministrator(administratorForm);
        return AdministratorMapper.INSTANCE.mapAdministratorToAdministratorDTO(administratorGateway.save(administrator));
    }

    @Override
    public PageResponse<AdministratorDTO> findAllAdministrators(final ParametersRequest parametersRequest) {
        Page<Administrator> pageAdministrators = administratorGateway.findAllAdministrators(parametersRequest);
        return PageResponse.buildPageResponse(pageAdministrators,
                AdministratorMapper.INSTANCE.mapAdministratorListToAdministratorDTOList(pageAdministrators.getContent()));
    }

    @Override
    public AdministratorDTO findAdministratorById(final Long administratorId) {
        return AdministratorMapper.INSTANCE
                .mapAdministratorToAdministratorDTO(administratorGateway.findAdministratorById(administratorId));
    }

    @Override
    public AdministratorDTO updateAdministrator(final AdministratorForm administratorForm, final Long administratorId) {
        final Administrator administrator =
                administratorGateway.updateAdministrator(
                        AdministratorMapper.INSTANCE.mapAdministratorFormToAdministrator(administratorForm),
                        administratorId);
        return AdministratorMapper.INSTANCE.mapAdministratorToAdministratorDTO(administrator);
    }

    @Override
    public void removeAdministrator(final Long administratorId) {
        administratorGateway.removeAdministrator(administratorId);
    }

    @Override
    public PageResponse<AdministratorDTO> searchAdministratorByName(final ParametersRequest parametersRequest,
                                                                    final String name) {
        final Page<Administrator> administratorPage = administratorGateway.searchAdministratorByName(parametersRequest, name);
        return PageResponse.buildPageResponse(administratorPage,
                AdministratorMapper.INSTANCE.mapAdministratorListToAdministratorDTOList(administratorPage.getContent()));
    }
}
