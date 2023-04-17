package com.hossein.PermissionTree.controller.viewModel.Product;

public class ProductViewModel {
	
	private Long productId;
	private String productName;
	private Double productPrice;
	private Integer productCount;
	private String productDateAdded;
	private String productDateModified;
	
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
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public String getProductDateAdded() {
		return productDateAdded;
	}
	public void setProductDateAdded(String productDateAdded) {
		this.productDateAdded = productDateAdded;
	}
	public String getProductDateModified() {
		return productDateModified;
	}
	public void setProductDateModified(String productDateModified) {
		this.productDateModified = productDateModified;
	}
	
}
