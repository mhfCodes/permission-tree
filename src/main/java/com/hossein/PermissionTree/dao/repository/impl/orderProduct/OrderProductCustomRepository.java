package com.hossein.PermissionTree.dao.repository.impl.orderProduct;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;

public interface OrderProductCustomRepository {
	
	List<OrderViewModel> findOrdersByProductIds(String productIds);

}
