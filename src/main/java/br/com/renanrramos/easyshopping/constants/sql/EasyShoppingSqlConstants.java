/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 28/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.constants.sql;

/**
 * @author renan.ramos
 *
 */
public class EasyShoppingSqlConstants {

	public static final String GET_CUSTOMER_BY_ID = "FROM Customer c WHERE c.id = :customerId";

	public static final String GET_SUBCATEGORIES_BY_NAME = "SELECT s FROM Subcategory s LEFT JOIN ProductCategory p ON p.id = s.productCategory.id WHERE s.name LIKE %:name% or p.name LIKE %:name%";

	public static final String GET_CUSTOMER_BY_NAME = "SELECT c FROM Customer c WHERE (c.name LIKE %:name% or c.cpf LIKE %:name% or c.email LIKE %:name%)";

	public static final String GET_COMPANY_BY_NAME = "SELECT c FROM Company c WHERE (c.name LIKE %:name% or c.registeredNumber LIKE %:name% or c.email LIKE %:name%)";

	public static final String GET_STORE_BY_NAME = "SELECT s FROM Store s LEFT JOIN User u ON u.tokenId = s.tokenId WHERE s.name LIKE %:name% or s.registeredNumber LIKE %:name% or s.corporateName LIKE %:name% or u.name LIKE %:name%";

	public static final String GET_STORE_WITH_COMPANY_ID = "SELECT s FROM Store s LEFT JOIN User u ON u.tokenId = s.tokenId WHERE (:name is null OR s.name LIKE %:name%  OR s.registeredNumber LIKE %:name% OR s.corporateName LIKE %:name% OR u.name LIKE %:name%) AND s.tokenId = :tokenId";

	public static final String GET_STORE_BY_COMPANY_ID = "SELECT s FROM Store s LEFT JOIN User u ON u.tokenId = s.tokenId WHERE s.tokenId = :tokenId";

	private EasyShoppingSqlConstants() {
		// Intentionally empty
	}
}
