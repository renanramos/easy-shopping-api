package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.core.gateway.AdministratorGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AdministratorEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AdministratorMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AdministratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
public class AdministratorGatewayImpl implements AdministratorGateway {

    private final AdministratorRepository administratorRepository;

    @Override
    public Administrator save(final Administrator administrator) {
        final AdministratorEntity administratorEntity = administratorRepository.save(AdministratorMapper.INSTANCE
                .mapAdministratorToAdministratorEntity(administrator));
        return AdministratorMapper.INSTANCE.mapAdministratorEntityToAdministrator(administratorEntity);
    }

    @Override
    public Page<Administrator> findAllAdministrators(final ParametersRequest parametersRequest) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        final Page<AdministratorEntity> administratorEntityPage = administratorRepository.findAll(page);
        final List<Administrator> administrators =
                AdministratorMapper.INSTANCE
                        .mapAdministratorEntityListToAdministratorList(administratorEntityPage.getContent());
        return new PageImpl<>(administrators, administratorEntityPage.getPageable(),
                administratorEntityPage.getTotalElements());
    }

    @Override
    public Administrator findAdministratorById(final Long administratorId) {
        return AdministratorMapper.INSTANCE
                .mapAdministratorEntityToAdministrator(getAdministratorOrThrow(administratorId));
    }

    @Override
    public Administrator updateAdministrator(final Administrator administrator, final Long administratorId) {
        final AdministratorEntity administratorEntity = getAdministratorOrThrow(administratorId);
        AdministratorMapper.INSTANCE.mapAdministratorToUpdateAdministratorEntity(administratorEntity, administrator);
        return AdministratorMapper.INSTANCE
                .mapAdministratorEntityToAdministrator(administratorRepository.save(administratorEntity));
    }

    @Override
    public void removeAdministrator(final Long administratorId) {
        getAdministratorOrThrow(administratorId);
        administratorRepository.removeById(administratorId);
    }

    @Override
    public Page<Administrator> searchAdministratorByName(final ParametersRequest parametersRequest, final String name) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        final Page<AdministratorEntity> administratorEntityPage =
                administratorRepository.findAdministratorByNameContains(page, name);
        final List<Administrator> administrators =
                AdministratorMapper.INSTANCE
                        .mapAdministratorEntityListToAdministratorList(administratorEntityPage.getContent());
        return new PageImpl<>(administrators, administratorEntityPage.getPageable(),
                administratorEntityPage.getTotalElements());
    }

    private AdministratorEntity getAdministratorOrThrow(final Long administratorId) {
        return administratorRepository.findById(administratorId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessagesConstants.ADMINISTRATOR_NOT_FOUND));
    }
}
