package com.hossein.PermissionTree.dto.order;

import java.util.Set;

import com.hossein.PermissionTree.dto.orderProduct.OrderProductDto;
import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.model.order.OrderStatus;

public class OrderDto {
	
	private Long id;
	private Long totalPrice;
	private UserDto user;
	private OrderStatus status;
	private String description;
	private String orderDate;
	private Set<OrderProductDto> orderProducts;
	private String productIds;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public Set<OrderProductDto> getOrderProducts() {
		return orderProducts;
	}
	public void setOrderProducts(Set<OrderProductDto> orderProducts) {
		this.orderProducts = orderProducts;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

}
