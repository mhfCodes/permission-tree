package com.hossein.PermissionTree.controller.viewModel;

public class ProductViewModel {
	
	private Long productId;
	private String productName;
	private double productPrice;
	private int productCount;
	private String productDateAdded;
	
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
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public String getProductDateAdded() {
		return productDateAdded;
	}
	public void setProductDateAdded(String productDateAdded) {
		this.productDateAdded = productDateAdded;
	}
	
}
