package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.core.gateway.AdministratorGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
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
    public Administrator save(final Administrator administrator) {
        final AdministratorEntity administratorEntity = administratorRepository.save(AdministratorMapper.INSTANCE
                .mapAdministratorToAdministratorEntity(administrator));
        return AdministratorMapper.INSTANCE.mapAdministratorEntityToAdministrator(administratorEntity);
    }

    @Override
    public PageResponse<Administrator> findAllAdministrators(final Integer pageNumber,
                                                             final Integer pageSize,
                                                             final String sortBy) {
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();
        final Page<AdministratorEntity> administratorEntityPage = administratorRepository.findAll(page);
        return PageResponse.buildPageResponse(administratorEntityPage,
                AdministratorMapper.INSTANCE.mapAdministratorEntityListToAdministratorList(administratorEntityPage.getContent()));
    }

    @Override
    public Administrator findAdministratorById(final Long administratorId) {
        final AdministratorEntity administratorEntity = getAdministratorOrThrow(administratorId);
        return AdministratorMapper.INSTANCE.mapAdministratorEntityToAdministrator(administratorEntity);
    }

    @Override
    public Administrator updateAdministrator(final Administrator administrator, final Long administratorId) {
        final AdministratorEntity administratorEntity = getAdministratorOrThrow(administratorId);
        AdministratorMapper.INSTANCE.mapAdministratorEntityToUpdateAdministrator(administratorEntity, administrator);
        return AdministratorMapper.INSTANCE
                .mapAdministratorEntityToAdministrator(administratorRepository.save(administratorEntity));
    }

    @Override
    public void removeAdministrator(final Long administratorId) {
        getAdministratorOrThrow(administratorId);
        administratorRepository.removeById(administratorId);
    }

    @Override
    public PageResponse<Administrator> searchAdministratorByName(final ParametersRequest parametersRequest, final String name) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        final Page<AdministratorEntity> administratorByName =
                administratorRepository.findAdministratorByNameContains(page, name);
        return PageResponse.buildPageResponse(administratorByName,
                AdministratorMapper.INSTANCE
                        .mapAdministratorEntityListToAdministratorList(administratorByName.getContent()));
    }

    private AdministratorEntity getAdministratorOrThrow(final Long administratorId) {
        return administratorRepository.findById(administratorId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessagesConstants.ADMINISTRATOR_NOT_FOUND));
    }
}
