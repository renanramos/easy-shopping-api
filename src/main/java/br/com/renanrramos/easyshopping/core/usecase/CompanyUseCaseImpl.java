package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.core.gateway.CompanyGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CompanyMapper;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
public class CompanyUseCaseImpl implements CompanyUseCase {

    private final CompanyGateway companyGateway;

    private final AuthenticationServiceImpl authenticationService;

    @Override
    public CompanyDTO saveCompany(final CompanyForm companyForm) throws EasyShoppingException {
        final Company company = companyGateway.saveCompany(
                CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm));
        return CompanyMapper.INSTANCE.mapCompanyToCompanyDTO(company);
    }

    @Override
    public PageResponse<CompanyDTO> findCompanies(final ParametersRequest parametersRequest) {
        final Page<Company> companies = companyGateway.findCompanies(parametersRequest);
        return new PageResponse<>(companies.getTotalElements(), companies.getTotalPages(),
                CompanyMapper.INSTANCE.mapCompanyListToCompanyDTOList(companies.getContent()));
    }

    @Override
    public PageResponse<CompanyDTO> getCompanyByNameRegisteredNumberOrEmail(final ParametersRequest parametersRequest,
                                                                            final String name) {
        final Page<Company> companies =
                companyGateway.getCompanyByNameRegisteredNumberOrEmail(parametersRequest, name);
        return new PageResponse<>(companies.getTotalElements(), companies.getTotalPages(),
                CompanyMapper.INSTANCE.mapCompanyListToCompanyDTOList(companies.getContent()));
    }

    @Override
    public CompanyDTO findCompanyByTokenId(final String tokenId) {
        return CompanyMapper.INSTANCE
                .mapCompanyToCompanyDTO(companyGateway.findCompanyByTokenId(tokenId));
    }

    @Override
    public CompanyDTO updateCompany(final CompanyForm companyForm, final Long companyId) {

        final String tokenId = authenticationService.getName();
        if (companyGateway.findCompanyByTokenId(tokenId) != null) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.ACCOUNT_NOT_FOUND);
        }

        final Company company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);
        company.setTokenId(tokenId);
        return CompanyMapper.INSTANCE.mapCompanyToCompanyDTO(companyGateway.updateCompany(company, companyId));
    }

    @Override
    public void removeCompany(final Long companyId) {
        companyGateway.removeCompany(companyId);
    }
}