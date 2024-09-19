/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 28/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository.constants;

/**
 * @author renan.ramos
 *
 */
public class EasyShoppingSqlConstants {

	public static final String GET_CUSTOMER_BY_ID = "FROM Customer c WHERE c.id = :customerId";

	public static final String GET_SUBCATEGORIES_BY_NAME = "SELECT s FROM SubCategory s LEFT JOIN ProductCategory p ON p.id = s.productCategory.id WHERE s.name LIKE %:name% or p.name LIKE %:name%";

	public static final String GET_CUSTOMER_BY_NAME = "SELECT c FROM Customer c WHERE (c.name LIKE %:name% or c.cpf LIKE %:name% or c.email LIKE %:name%)";

	public static final String GET_COMPANY_BY_NAME = "SELECT c FROM Company c WHERE (c.name LIKE %:name% or c.registeredNumber LIKE %:name% or c.email LIKE %:name%)";

	public static final String GET_STORE_BY_NAME = "SELECT s FROM Store s LEFT JOIN UserEntity u ON u.tokenId = s.tokenId WHERE (:name is null OR s.name LIKE %:name% or s.registeredNumber LIKE %:name% or s.corporateName LIKE %:name% or u.name LIKE %:name%) AND s.tokenId = :tokenId";

	public static final String GET_STORE_BY_NAME_CNPJ_REGISTERED_NAME = "SELECT s FROM Store s LEFT JOIN UserEntity u ON u.tokenId = s.tokenId WHERE s.name LIKE %:name% or s.registeredNumber LIKE %:name% or s.corporateName LIKE %:name% or u.name LIKE %:name%";

	public static final String GET_STORE_WITHOUT_TOKEN_ID = "SELECT s FROM Store s WHERE (:name is null OR s.name LIKE %:name%  OR s.registeredNumber LIKE %:name% OR s.corporateName LIKE %:name%)";

	public static final String GET_STORE_BY_COMPANY_ID = "SELECT s FROM Store s LEFT JOIN UserEntity u ON u.tokenId = s.tokenId WHERE s.tokenId = :tokenId";

	public static final String GET_STOCKS_BY_NAME = "SELECT s FROM Stock s LEFT JOIN Store store ON store.id = s.store.id WHERE s.name LIKE %:name% OR store.name LIKE %:name%";

	public static final String GET_STOCK_ITEM_LEFT_JOIN_PRODUCT = "SELECT si FROM StockItem si LEFT JOIN Product p ON si.productId = p.id";

	public static final String GET_STOCK_ITEM_BY_PRODUCT_NAME = "SELECT si FROM StockItem si LEFT JOIN Stock s ON s.id = si.stock.id LEFT JOIN Product p ON p.id = si.productId WHERE p.name LIKE %:name% AND s.id = :stockId";

	public static final String GET_PRODUCT_BY_NAME = "SELECT p FROM Product p LEFT JOIN SubCategory s ON s.id = p.subCategory.id WHERE s.name LIKE %:name% OR p.name LIKE %:name%";

	public static final String GET_PRODUCT_BY_NAME_AND_COMPANY_ID = "Select p FROM Product p LEFT JOIN SubCategory s ON s.id = p.subCategory.id WHERE s.name LIKE %:name% OR p.name LIKE %:name% AND p.companyId = :tokenId";

	public static final String GET_CUSTOMER_ORDERS = "SELECT o FROM Order o WHERE o.customerId = :customerId";

	public static final String GET_ORDER_ITEMS_BY_ORDER_ID = "SELECT i FROM OrderItem i LEFT JOIN Order o ON i.order.id = o.id WHERE o.id = :orderId";

	public static final String GET_ORDER_ITEM_STATISTIC = "SELECT i FROM OrderItem i LEFT JOIN Order o ON i.order.id = o.id LEFT JOIN Purchase p ON o.purchase.id = p.id";

	private EasyShoppingSqlConstants() {
		// Intentionally empty
	}
}
