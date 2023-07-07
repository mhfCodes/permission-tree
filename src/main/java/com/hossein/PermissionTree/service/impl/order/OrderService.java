package com.hossein.PermissionTree.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.dao.repository.order.IOrderRepository;
import com.hossein.PermissionTree.model.order.OrderModel;
import com.hossein.PermissionTree.service.order.IOrderService;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private IOrderRepository iOrderRepository;
	
	@Override
	public List<OrderModel> getAll() {
		return this.iOrderRepository.findAll();
	}

}
