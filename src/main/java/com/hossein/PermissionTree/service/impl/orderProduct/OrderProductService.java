package com.hossein.PermissionTree.service.impl.orderProduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dao.repository.orderProduct.IOrderProductRepository;
import com.hossein.PermissionTree.service.orderProduct.IOrderProductService;

@Service
public class OrderProductService implements IOrderProductService {

	@Autowired
	private IOrderProductRepository iOrderProductRepository;

	@Override
	public List<OrderViewModel> findOrdersByProductIds(String productIds) {
		return this.iOrderProductRepository.findOrdersByProductIds(productIds);
	}
	
}
