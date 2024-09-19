package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.core.gateway.AdministratorGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AdministratorMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AdministratorRepository;

import br.com.renanrramos.easyshopping.model.AdministratorEntity;
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
        return buildPageResponse(administratorRepository.findAll(page));
    }

    @Override
    public Administrator findAdministratorById(final Long administratorId) {
        return administratorRepository.findById(administratorId)
                .map(AdministratorMapper.INSTANCE::mapAdministratorEntityToAdministrator)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessagesConstants.ADMINISTRATOR_NOT_FOUND));
    }

    @Override
    public Administrator updateAdministrator(final Administrator administrator, final Long administratorId) {
        final AdministratorEntity administratorEntity = getAdministratorEntity(administratorId);
        AdministratorMapper.INSTANCE.mapAdministratorEntityToUpdateAdministrator(administratorEntity, administrator);
        final AdministratorEntity updated = administratorRepository.save(administratorEntity);
        return AdministratorMapper.INSTANCE.mapAdministratorEntityToAdministrator(updated);
    }

    @Override
    public void removeAdministrator(final Long administratorId) {
        getAdministratorEntity(administratorId);
        administratorRepository.removeById(administratorId);
    }

    @Override
    public PageResponse<Administrator> searchAdministratorByName(final ParametersRequest parametersRequest, final String name) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        return buildPageResponse(administratorRepository.findAdministratorByNameContains(page, name));
    }

    private AdministratorEntity getAdministratorEntity(final Long administratorId) {
        return administratorRepository.findById(administratorId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessagesConstants.ADMINISTRATOR_NOT_FOUND));
    }

    private PageResponse<Administrator> buildPageResponse(final Page<AdministratorEntity> page) {
        return new PageResponse<>(page.getTotalElements(),
                page.getTotalPages(),
                AdministratorMapper.INSTANCE.mapAdministratorEntityListToAdministratorList(page.getContent()));
    }
}
