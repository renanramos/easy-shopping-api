package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.gateway.CompanyGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.CompanyEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CompanyMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
public class CompanyGatewayImpl implements CompanyGateway {

    private final CompanyRepository companyRepository;

    @Override
    public Company saveCompany(final Company company) throws EasyShoppingException {

        if (companyRepository.findTopCompanyByRegisteredNumber(company.getRegisteredNumber())
                .isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.CNPJ_ALREADY_EXIST);
        }

        final CompanyEntity companyEntity = CompanyMapper.INSTANCE.mapCompanyToCompanyEntity(company);
        return CompanyMapper.INSTANCE.mapCompanyEntityToCompany(companyRepository.save(companyEntity));
    }

    @Override
    public Page<Company> findCompanies(final ParametersRequest parametersRequest) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        final Page<CompanyEntity> companyEntityPage = companyRepository.findAll(page);
        return buildCompanyListPage(companyEntityPage);
    }

    @Override
    public Page<Company> getCompanyByNameRegisteredNumberOrEmail(final ParametersRequest parametersRequest,
                                                                 final String name) {
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        final Page<CompanyEntity> companyEntityPage =
                companyRepository.getCompanyByNameRegisteredNumberOrEmail(page, name);
        return buildCompanyListPage(companyEntityPage);
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
        final CompanyEntity companyToBeUpdated = getCompanyByCriteria(
                companyRepository.findById(companyId)
                        .orElse(null));
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
            throw new EntityNotFoundException(ExceptionConstantMessages.COMPANY_NOT_FOUND);
        }
        return companyEntity;
    }

    private static PageImpl<Company> buildCompanyListPage(final Page<CompanyEntity> companyEntityPage) {
        final List<Company> companies =
                CompanyMapper.INSTANCE.mapCompanyEntityListToCompanyList(companyEntityPage.getContent());
        return new PageImpl<>(companies, companyEntityPage.getPageable(), companyEntityPage.getTotalElements());
    }
}