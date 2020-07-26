/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.builder.StoreBuilder;

/**
 * @author renan.ramos
 *
 */
public class StoreForm {

	private String name;
	
	private String registeredNumber;
	
	private String corporateName;
	
	private Long companyId;

	public StoreForm() {
	}
	
	public StoreForm(String name, String registeredNumber, String corporateName,
			Long companyId) {
		this.name = name;
		this.registeredNumber = registeredNumber;
		this.corporateName = corporateName;
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public String getRegisteredNumber() {
		return registeredNumber;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegisteredNumber(String registeredNumber) {
		this.registeredNumber = registeredNumber;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public void setCompanyId(Long company) {
		this.companyId = company;
	}
	
	public static Store converterStoreFormToStore(StoreForm storeForm) {
		return StoreBuilder.builder()
				.withName(storeForm.getName())
				.withCorporateName(storeForm.getCorporateName())
				.withRegisteredNumber(storeForm.getRegisteredNumber())
				.build();
	}

	@Override
	public String toString() {
		return "StoreForm [name=" + name + ", registeredNumber=" + registeredNumber + ", corporateName=" + corporateName
				+ ", companyId=" + companyId + "]";
	}
}
