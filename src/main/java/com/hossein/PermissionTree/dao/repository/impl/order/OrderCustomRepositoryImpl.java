package com.hossein.PermissionTree.dao.repository.impl.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dao.config.GenericRepository;
import com.hossein.PermissionTree.dto.order.OrderDto;

@Repository
public class OrderCustomRepositoryImpl extends GenericRepository implements OrderCustomRepository {

	@Override
	public List<OrderViewModel> search(OrderDto dto) {
		
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		
		hql.append("select"
				+ " e.id as orderId, e.totalPrice as orderTotalPrice,"
				+ " e.status as orderStatus, e.description as orderDescription,"
				+ " e.orderDate as orderDate"
				+ " from OrderModel e"
				+ " where 1=1");
		
		if (dto.getTotalPrice() != null && dto.getTotalPrice() > -1) {
			hql.append(" and e.totalPrice = :totalPrice");
			params.put("totalPrice", dto.getTotalPrice());
		}
		
		if (dto.getStatus() != null) {
			hql.append(" and e.status = :status");
			params.put("status", dto.getStatus());
		}
		
		if (StringUtils.hasText(dto.getDescription())) {
			hql.append(" and e.description like :description");
			params.put("description", "%"+dto.getDescription()+"%");
		}
		
		if (StringUtils.hasText(dto.getOrderDate())) {
			hql.append(" and e.orderDate like :orderDate");
			params.put("orderDate", "%"+dto.getOrderDate()+"%");
		}
		
		return super.getAll(hql.toString(), params, OrderViewModel.class);
	}

}
