package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.AdministratorUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class AdministratorDelegateImpl implements AdministratorDelegate{

    private final AdministratorUseCase administratorUseCase;

    @Override
    public AdministratorDTO save(final AdministratorForm administratorForm) {
        return administratorUseCase.save(administratorForm);
    }

    @Override
    public PageResponse<AdministratorDTO> findAllAdministrators(final Integer pageNumber,
                                                                final Integer pageSize,
                                                                final String sortBy) {
        return administratorUseCase.findAllAdministrators(pageNumber, pageSize, sortBy);
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
    public List<AdministratorDTO> searchAdministratorByName(final String name) {
        return administratorUseCase.searchAdministratorByName(name);
    }
}
