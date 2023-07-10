package com.hossein.PermissionTree.service.impl.orderProduct;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dao.repository.orderProduct.IOrderProductRepository;
import com.hossein.PermissionTree.model.order.OrderProduct;
import com.hossein.PermissionTree.service.orderProduct.IOrderProductService;

@Service
public class OrderProductService implements IOrderProductService {

	@Autowired
	private IOrderProductRepository iOrderProductRepository;

	@Override
	public List<OrderViewModel> findOrdersByProductIds(String productIds) {
		return this.iOrderProductRepository.findOrdersByProductIds(productIds);
	}

	@Override
	@Transactional
	public void saveAll(Collection<OrderProduct> entities) {
		this.iOrderProductRepository.saveAll(entities);
	}
	
}
