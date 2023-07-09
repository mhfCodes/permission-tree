package com.hossein.PermissionTree.dao.repository.impl.orderProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dao.config.GenericRepository;

@Repository
public class OrderProductCustomRepositoryImpl extends GenericRepository implements OrderProductCustomRepository {

	@Override
	public List<OrderViewModel> findOrdersByProductIds(String productIds) {
		
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		
		hql.append("select"
				+ " om.id as orderId, om.totalPrice as orderTotalPrice,"
				+ " om.status as orderStatus, om.description as orderDescription,"
				+ " om.orderDate as orderDate"
				+ " from OrderProduct e"
				+ " left join OrderModel om on om.id = e.order.id"
				+ " left join Product p on p.id = e.product.id"
				+ " where 1=1");
		
		if (StringUtils.hasText(productIds)) {
			hql.append(" and p.id in (" + productIds + ")");
		}
		
		return super.getAll(hql.toString(), params, OrderViewModel.class);
	}

}
