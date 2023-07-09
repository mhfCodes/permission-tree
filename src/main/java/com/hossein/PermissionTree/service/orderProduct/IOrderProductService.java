package com.hossein.PermissionTree.service.orderProduct;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;

public interface IOrderProductService {
	
	List<OrderViewModel> findOrdersByProductIds(String productIds);

}
