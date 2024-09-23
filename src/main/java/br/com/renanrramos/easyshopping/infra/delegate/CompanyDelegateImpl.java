package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.CompanyUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class CompanyDelegateImpl implements CompanyDelegate{

    private final CompanyUseCase companyUseCase;

    @Override
    public CompanyDTO saveCompany(final CompanyForm company) throws EasyShoppingException {
        return companyUseCase.saveCompany(company);
    }

    @Override
    public PageResponse<CompanyDTO> findCompanies(final ParametersRequest parametersRequest, final String name) {
        return StringUtils.isEmpty(name) ?
                findCompanies(parametersRequest) :
                getCompanyByNameRegisteredNumberOrEmail(parametersRequest, name);
    }

    private PageResponse<CompanyDTO> findCompanies(final ParametersRequest parametersRequest) {
        return companyUseCase.findCompanies(parametersRequest);
    }

    private PageResponse<CompanyDTO> getCompanyByNameRegisteredNumberOrEmail(final ParametersRequest parametersRequest,
                                                                         final String name) {
        return companyUseCase.getCompanyByNameRegisteredNumberOrEmail(parametersRequest, name);
    }

    @Override
    public CompanyDTO findCompanyByTokenId(final String tokenId) {
        return companyUseCase.findCompanyByTokenId(tokenId);
    }

    @Override
    public CompanyDTO updateCompany(final CompanyForm companyForm, final Long companyId) {
        return companyUseCase.updateCompany(companyForm, companyId);
    }

    @Override
    public void removeCompany(final Long companyId) {
        companyUseCase.removeCompany(companyId);
    }
}