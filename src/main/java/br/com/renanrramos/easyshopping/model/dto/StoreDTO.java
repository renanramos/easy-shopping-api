/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.Store;

/**
 * @author renan.ramos
 *
 */
public class StoreDTO {

	private Long id;
	
	private String name;
	
	private String registeredNumber;
	
	private String corporateName;
	
	private List<ProductDTO> products;
	
	private Company company;
	
	public StoreDTO() {
	
	}
	
	public StoreDTO(Store store) {
		this.id = store.getId();
		this.name = store.getName();
		this.registeredNumber = store.getRegisteredNumber();
		this.corporateName = store.getCorporateName();
		this.products = store.getProducts().stream().map(ProductDTO::new).collect(Collectors.toList());
		this.company = store.getCompany();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<ProductDTO> getProducts() {
		return products;
	}

	public Company getCompany() {
		return company;
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

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public static List<StoreDTO> converterStoreListToStoreDTOList(List<Store> stores) {
		return stores.stream().map(StoreDTO::new).collect(Collectors.toList());
	}
	
	public static StoreDTO converterStoreToStoreDTO(Store store) {
		return new StoreDTO(store);
	}

	@Override
	public String toString() {
		return "StoreDTO [id=" + id + ", name=" + name + ", registeredNumber=" + registeredNumber + ", corporateName="
				+ corporateName + ", products=" + products + ", company=" + company + "]";
	}

}
