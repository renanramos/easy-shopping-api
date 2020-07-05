/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.List;
import java.util.stream.Collectors;

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
	
	private List<ProductForm> products;
	
	private Long companyId;

	public StoreForm() {
	}
	
	public StoreForm(String name, String registeredNumber, String corporateName, List<ProductForm> products,
			Long companyId) {
		this.name = name;
		this.registeredNumber = registeredNumber;
		this.corporateName = corporateName;
		this.products = products;
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

	public List<ProductForm> getProducts() {
		return products;
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

	public void setProducts(List<ProductForm> products) {
		this.products = products;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public static Store converterStoreFormToStore(StoreForm storeForm) {
		return StoreBuilder.builder()
				.withName(storeForm.getName())
				.withCorporateName(storeForm.getCorporateName())
				.withProducts(storeForm.getProducts().stream().map(ProductForm::converterProductFormToProduct).collect(Collectors.toList()))
				.withRegisteredNumber(storeForm.getRegisteredNumber())
				.build();
	}

	@Override
	public String toString() {
		return "StoreForm [name=" + name + ", registeredNumber=" + registeredNumber + ", corporateName=" + corporateName
				+ ", products=" + products.size() + ", companyId=" + companyId + "]";
	}
}
