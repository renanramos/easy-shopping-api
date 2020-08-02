/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 30/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.builder.StoreBuilder;

/**
 * @author renan.ramos
 *
 */
public class CompanyDTO {

	private Long id;
	
	private String name;
	
	private String registeredNumber;
	
	private String email;
	
	private String phone;

	private Profile profile;
	
	private Set<Store> stores;
	
	public CompanyDTO() {
	}
	
	public CompanyDTO(Company company) {
		this.id = company.getId();
		this.name = company.getName();
		this.registeredNumber = company.getRegisteredNumber();
		this.email = company.getEmail();
		this.phone = company.getPhone();
		this.profile = company.getProfile();
		this.stores = company.getStores().isEmpty() 
					? null 
					: company.getStores().stream().map(store -> getStoreInfo(store)).collect(Collectors.toSet());
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

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegisteredNumber(String registeredNumber) {
		this.registeredNumber = registeredNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Set<Store> getStores() {
		return stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

	public static List<CompanyDTO> converterCompanyListToCompanyDTOList(List<Company> companies) {
		return companies.stream().map(CompanyDTO::new).collect(Collectors.toList());
	}
	
	public static CompanyDTO converterToCompanyDTO(Company company) {
		return new CompanyDTO(company);
	}

	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", name=" + name + ", registeredNumber=" + registeredNumber + ", email=" + email
				+ ", phone=" + phone + ", profile=" + profile + ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getRegisteredNumber()=" + getRegisteredNumber() + ", getEmail()=" + getEmail() + ", getPhone()="
				+ getPhone() + ", getProfile()=" + getProfile() + "]";
	}

	
	private static Store getStoreInfo(Store store) {
		return StoreBuilder.builder()
				.withCorporateName(store.getCorporateName())
				.withName(store.getName())
				.withRegisteredNumber(store.getRegisteredNumber())
				.build();
	}
}
