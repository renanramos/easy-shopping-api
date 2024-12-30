package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.core.gateway.AdministratorGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AdministratorEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AdministratorMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AdministratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
public class AdministratorGatewayImpl implements AdministratorGateway {

    private final AdministratorRepository administratorRepository;

    @Override
    public AdministratorEntity save(final Administrator administrator) {
        return administratorRepository.save(AdministratorMapper.INSTANCE
                .mapAdministratorToAdministratorEntity(administrator));
    }

    @Override
    public Page<AdministratorEntity> findAllAdministrators(final ParametersRequest parametersRequest) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        return administratorRepository.findAll(page);
    }

    @Override
    public AdministratorEntity findAdministratorById(final Long administratorId) {
        return getAdministratorOrThrow(administratorId);
    }

    @Override
    public AdministratorEntity updateAdministrator(final AdministratorForm administratorForm, final Long administratorId) {
        final AdministratorEntity administratorEntity = getAdministratorOrThrow(administratorId);
        AdministratorMapper.INSTANCE.mapAdministratorEntityToUpdateAdministratorForm(administratorEntity, administratorForm);
        return administratorRepository.save(administratorEntity);
    }

    @Override
    public void removeAdministrator(final Long administratorId) {
        getAdministratorOrThrow(administratorId);
        administratorRepository.removeById(administratorId);
    }

    @Override
    public Page<AdministratorEntity> searchAdministratorByName(final ParametersRequest parametersRequest, final String name) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        return administratorRepository.findAdministratorByNameContains(page, name);
    }

    private AdministratorEntity getAdministratorOrThrow(final Long administratorId) {
        return administratorRepository.findById(administratorId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessagesConstants.ADMINISTRATOR_NOT_FOUND));
    }
}
