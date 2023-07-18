package com.hossein.PermissionTree.model.order;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
	CANCELED(0), COMPLETED(1), FAILED(2), ONHOLD(3), PROCESSING(4);
	
	private int orderStatusValue;
	private static Map<String, OrderStatus> namesMap = new HashMap<>();
	
	static {
		namesMap.put("CANCELED", CANCELED);
		namesMap.put("COMPLETED", COMPLETED);
		namesMap.put("FAILED", FAILED);
		namesMap.put("ONHOLD", ONHOLD);
		namesMap.put("PROCESSING", PROCESSING);
	}
	
	@JsonCreator
	public static OrderStatus forValue(String value) {
		return namesMap.get(value.toLowerCase());
	}
	
	@JsonValue
	public String toValue() {
		for (Map.Entry<String, OrderStatus> entry : namesMap.entrySet()) {
			if (entry.getValue() == this) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	private OrderStatus(int orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}

	public int getOrderStatusValue() {
		return orderStatusValue;
	}

}
