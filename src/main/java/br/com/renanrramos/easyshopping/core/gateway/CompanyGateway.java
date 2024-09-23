package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface CompanyGateway {

    Company saveCompany(final Company company);

    PageResponse<Company> findCompanies(final ParametersRequest parametersRequest);

    PageResponse<Company> getCompanyByNameRegisteredNumberOrEmail(final ParametersRequest parametersRequest,
                                                                  final String name);

    Company findCompanyByTokenId(final String tokenId);

    Company updateCompany(final Company company, final Long companyId);

    boolean findTopCompanyByRegisteredNumber(final String registeredNumber);

    void removeCompany(final Long companyId);
}