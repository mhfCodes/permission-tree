package com.hossein.PermissionTree.controller.viewModel.Order;

import java.util.Set;

import com.hossein.PermissionTree.controller.viewModel.OrderProduct.OrderProductViewModel;
import com.hossein.PermissionTree.model.order.OrderStatus;

public class OrderViewModel {
	
	private Long orderId;
	private Long orderTotalPrice;
	private OrderStatus orderStatus;
	private String orderDescription;
	private String orderDate;
	private Set<OrderProductViewModel> orderProducts;
	
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(Long orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderDescription() {
		return orderDescription;
	}
	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public Set<OrderProductViewModel> getOrderProducts() {
		return orderProducts;
	}
	public void setOrderProducts(Set<OrderProductViewModel> orderProducts) {
		this.orderProducts = orderProducts;
	}

}
