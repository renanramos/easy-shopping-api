package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.core.gateway.CompanyGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CompanyMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.CompanyRepository;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.CompanyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
public class CompanyGatewayImpl implements CompanyGateway {

    private final CompanyRepository companyRepository;

    @Override
    public Company saveCompany(final Company company) {
        final CompanyEntity companyEntity = CompanyMapper.INSTANCE.mapCompanyToCompanyEntity(company);
        return CompanyMapper.INSTANCE.mapCompanyEntityToCompany(companyRepository.save(companyEntity));
    }

    @Override
    public PageResponse<Company> findCompanies(final ParametersRequest parametersRequest) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        return buildPageResponse(companyRepository.findAll(page));
    }

    @Override
    public PageResponse<Company> getCompanyByNameRegisteredNumberOrEmail(final ParametersRequest parametersRequest,
                                                                         final String name) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        final Page<CompanyEntity> companyEntityPage =
                companyRepository.getCompanyByNameRegisteredNumberOrEmail(page, name);
        return buildPageResponse(companyEntityPage);
    }

    @Override
    public Company findCompanyByTokenId(final String tokenId) {
        final CompanyEntity companyEntity =
                getCompanyByCriteria(companyRepository.findCompanyByTokenId(tokenId)
                        .orElse(null));
        return CompanyMapper.INSTANCE.mapCompanyEntityToCompany(companyEntity);
    }

    @Override
    public Company updateCompany(final Company company, final Long companyId) {
        final CompanyEntity companyToBeUpdated = getCompanyByCriteria(companyRepository.findById(companyId).orElse(null));
        CompanyMapper.INSTANCE.mapCompanyToUpdateCompanyEntity(companyToBeUpdated, company);
        return CompanyMapper.INSTANCE.mapCompanyEntityToCompany(companyRepository.save(companyToBeUpdated));
    }

    @Override
    public boolean findTopCompanyByRegisteredNumber(final String registeredNumber) {
        return companyRepository.findTopCompanyByRegisteredNumber(registeredNumber).isPresent();
    }

    @Override
    public void removeCompany(final Long companyId) {
        final CompanyEntity companyEntity = getCompanyByCriteria(companyRepository.findById(companyId)
                .orElse(null));
        companyRepository.deleteById(companyEntity.getId());
    }

    private CompanyEntity getCompanyByCriteria(final CompanyEntity companyEntity) {
        if (companyEntity == null) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.COMPANY_NOT_FOUND);
        }
        return companyEntity;
    }

    private PageResponse<Company> buildPageResponse(final Page<CompanyEntity> companyEntityPage) {
        return new PageResponse<>(companyEntityPage.getTotalElements(),
                companyEntityPage.getTotalPages(),
                CompanyMapper.INSTANCE.mapCompanyEntityListToCompanyList(companyEntityPage.getContent()));
    }
}