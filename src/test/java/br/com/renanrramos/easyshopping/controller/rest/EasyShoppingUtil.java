/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 26/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.Purchase;
import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.builder.StoreBuilder;
import br.com.renanrramos.easyshopping.model.form.CreditCardForm;
import br.com.renanrramos.easyshopping.model.form.OrderItemForm;
import br.com.renanrramos.easyshopping.model.form.StockItemForm;

/**
 * @author renan.ramos
 *
 */
public class EasyShoppingUtil {

	public static Address getAddressInstance() {
		Address address = new Address();
		address.setId(1L);
		address.setCep("cep");
		address.setDistrict("district");
		address.setNumber(123L);
		address.setState("state");
		address.setStreetName("streetName");
		address.setCustomerId("customerId");
		return address;
	}

	
	public static Customer getCustomerInstance() {
		Customer customer = new Customer();
		customer.setCpf("12345684522");
		customer.setEmail("email@mail.com");
		customer.setId(1L);
		customer.setName("name");
		customer.setProfile(Profile.CUSTOMER);
		return customer;
	}

	public static Company getCompanyInstance() {
		Company company = new Company();
		company.setId(1L);
		company.setName("Teste");
		company.setPhone("13213321");
		company.setProfile(Profile.COMPANY);
		company.setRegisteredNumber("1315adsd5");
		company.setEmail("company@mail.com");
		return company;
	}

	public static CreditCard getCreditCardInstance(Long id) {
		final CreditCard creditCard = new CreditCard();
		creditCard.setId(id);
		creditCard.setCode(123);
		creditCard.setCreditCardNumber("1234 1234 1234 1234");
		creditCard.setOwnerName("OWNER NAME");
		return creditCard;
	}

	public static CreditCardForm getCreditCardForm() {
		CreditCardForm creditCard = new CreditCardForm();
		creditCard.setCode(123);
		creditCard.setCreditCardNumber("1234 1234 1234 1234");
		creditCard.setOwnerName("OWNER NAME");
		creditCard.setValidDate("2031-01-02");
		return creditCard;
	}

	public static OrderItem getOrderItemInstance() {
		final OrderItem orderItem = new OrderItem();
		orderItem.setAmount(2);
		orderItem.setPrice(20.0);
		orderItem.setProductId(2L);
		orderItem.setTotal(40.0);
		orderItem.setOrder(getOrderInstance());
		return orderItem;
	}

	public static OrderItemForm getOrderItemFormInstance() {

		final OrderItemForm orderItemForm = new OrderItemForm();
		orderItemForm.setOrderId(1L);
		orderItemForm.setProductId(1L);
		orderItemForm.setAmount(2);
		orderItemForm.setPrice(3.0);
		orderItemForm.setTotal(6.0);
		orderItemForm.setProductName("productName");
		return orderItemForm;
	}

	public static Order getOrderInstance() {
		final Order order = new Order();
		order.setOrderNumber("orderNumber");
		order.setCustomerId("customerId");
		return order;
	}

	public static Product getProductInstance() {
		final Product product = new Product();
		product.setCompanyId("companyId");
		product.setDescription("description");
		product.setId(1L);
		product.setName("productName");
		product.setPrice(20.0);
		return product;
	}

	public static Purchase getPurchaseInstance(Long id) {
		final Purchase purchase = new Purchase();
		purchase.setId(id);
		purchase.setAddress(getAddressInstance());
		purchase.setCreditCard(getCreditCardInstance(1L));
		purchase.setCustomerId("customerId");
		purchase.setOrder(getOrderInstance());
		return purchase;
	}

	public static Store getStoreInstance(Long id) {
		return StoreBuilder.builder().withId(id).withCorporateName("corporateName").withName("name")
				.withRegisteredNumber("registeredNumber").build();
	}

	public static Stock getStockInstance(Long id) {
		Stock stock = new Stock();
		stock.setId(id);
		stock.setName("stockName");
		return stock;
	}

	public static StockItemForm getStockItemForm(Long productId, Long stockId) {
		final StockItemForm stockItemForm = new StockItemForm();
		stockItemForm.setStockId(stockId);
		stockItemForm.setMaxAmount(20.0);
		stockItemForm.setCurrentAmount(10);
		stockItemForm.setMinAmount(5.0);
		stockItemForm.setProductId(productId);
		stockItemForm.setProductName("productName");
		return  stockItemForm;
	}

	public static StockItem getStockItemInstance() {
		final StockItem stockItem = new StockItem();
		stockItem.setId(1L);
		stockItem.setCurrentAmount(10);
		stockItem.setMaxAmount(20.0);
		stockItem.setMinAmount(5.0);
		stockItem.setProductName("productName");
		return stockItem;
	}

	public static User getUserInstance() {
		User user = new User();
		user.setEmail("user@mail.com");
		user.setId(1L);
		user.setName("user");
		user.setProfile(Profile.ADMINISTRATOR);
		user.setSync(true);
		user.setTokenId("tokenId");
		return user;
	}
}
