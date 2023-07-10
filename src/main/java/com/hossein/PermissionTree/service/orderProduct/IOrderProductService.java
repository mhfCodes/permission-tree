package com.hossein.PermissionTree.service.orderProduct;

import java.util.Collection;
import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.model.order.OrderProduct;

public interface IOrderProductService {
	
	List<OrderViewModel> findOrdersByProductIds(String productIds);
	
	void saveAll(Collection<OrderProduct> entities);

}
