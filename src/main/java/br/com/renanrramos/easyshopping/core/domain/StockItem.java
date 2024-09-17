/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

/**
 * @author renan.ramos
 *
 */
public class StockItem {

	private Long id;
	private Stock stock;
	private Long productId;
	private String productName;
	private Double maxAmount;
	private Double minAmount;
	private Integer currentAmount;

	public StockItem() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	public Integer getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Integer currentAmount) {
		this.currentAmount = currentAmount;
	}

	@Override
	public String toString() {
		return "StockItem [id=" + id + ", stock=" + stock + ", productId=" + productId + ", maxAmount=" + maxAmount
				+ ", minAmount=" + minAmount + ", currentAmount=" + currentAmount + "]";
	}
}
