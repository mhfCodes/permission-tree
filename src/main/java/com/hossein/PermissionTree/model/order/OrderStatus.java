package com.hossein.PermissionTree.model.order;

public enum OrderStatus {
	CANCELED(0), COMPLETED(1), FAILED(2), ONHOLD(3), PROCESSING(4);
	
	private int orderStatusValue;
	
	private OrderStatus(int orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}

	public int getOrderStatusValue() {
		return orderStatusValue;
	}

}
