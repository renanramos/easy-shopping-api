package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import org.springframework.data.domain.Page;

public interface CompanyGateway {

    Company saveCompany(final Company company) throws EasyShoppingException;

    Page<Company> findCompanies(final ParametersRequest parametersRequest);

    Page<Company> getCompanyByNameRegisteredNumberOrEmail(final ParametersRequest parametersRequest,
                                                          final String name);

    Company findCompanyByTokenId(final String tokenId);

    Company updateCompany(final Company company, final Long companyId);

    boolean findTopCompanyByRegisteredNumber(final String registeredNumber);

    void removeCompany(final Long companyId);
}