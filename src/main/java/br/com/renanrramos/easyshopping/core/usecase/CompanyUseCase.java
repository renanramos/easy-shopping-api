package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;

public interface CompanyUseCase {
    CompanyDTO saveCompany(final CompanyForm companyForm) throws EasyShoppingException;

    PageResponse<CompanyDTO> findCompanies(final ParametersRequest parametersRequest);

    PageResponse<CompanyDTO> getCompanyByNameRegisteredNumberOrEmail(final ParametersRequest parametersRequest,
                                                                  final String name);

    CompanyDTO findCompanyByTokenId(final String tokenId);

    CompanyDTO updateCompany(final CompanyForm companyForm, final Long companyId);

    void removeCompany(final Long companyId);
}