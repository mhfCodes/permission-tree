package com.hossein.PermissionTree.dao.repository.impl.order;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dto.order.OrderDto;

public interface OrderCustomRepository {

	List<OrderViewModel> search(OrderDto dto);
}
