package com.hossein.PermissionTree.service.order;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dto.order.OrderDto;
import com.hossein.PermissionTree.model.order.OrderModel;

public interface IOrderService {
	
	List<OrderModel> getAll();

	List<OrderViewModel> search(OrderDto dto);

	OrderModel load(Long id);
	
	long save(OrderDto dto);
	
	Boolean cancelOrder(Long id);
}
