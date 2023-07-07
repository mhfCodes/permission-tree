package com.hossein.PermissionTree.controller.viewModel.OrderProduct;

import com.hossein.PermissionTree.controller.viewModel.Product.ProductViewModel;

public class OrderProductViewModel {
	
	private Long orderProductId;
	private ProductViewModel product;
	private Long orderProductPrice;
	private Long orderProductCount;
	private Long orderProductTotalPrice;
	public Long getOrderProductId() {
		return orderProductId;
	}
	public void setOrderProductId(Long orderProductId) {
		this.orderProductId = orderProductId;
	}
	public ProductViewModel getProduct() {
		return product;
	}
	public void setProduct(ProductViewModel product) {
		this.product = product;
	}
	public Long getOrderProductPrice() {
		return orderProductPrice;
	}
	public void setOrderProductPrice(Long orderProductPrice) {
		this.orderProductPrice = orderProductPrice;
	}
	public Long getOrderProductCount() {
		return orderProductCount;
	}
	public void setOrderProductCount(Long orderProductCount) {
		this.orderProductCount = orderProductCount;
	}
	public Long getOrderProductTotalPrice() {
		return orderProductTotalPrice;
	}
	public void setOrderProductTotalPrice(Long orderProductTotalPrice) {
		this.orderProductTotalPrice = orderProductTotalPrice;
	}

}
