package com.hossein.PermissionTree.service.impl.order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dao.repository.order.IOrderRepository;
import com.hossein.PermissionTree.dto.order.OrderDto;
import com.hossein.PermissionTree.exception.ApplicationException;
import com.hossein.PermissionTree.model.order.OrderModel;
import com.hossein.PermissionTree.service.order.IOrderService;
import com.hossein.PermissionTree.service.orderProduct.IOrderProductService;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private IOrderRepository iOrderRepository;
	
	@Autowired
	private IOrderProductService iOrderProductService;
	
	@Override
	public List<OrderModel> getAll() {
		return this.iOrderRepository.findAll();
	}

	@Override
	public List<OrderViewModel> search(OrderDto dto) {
		
		List<OrderViewModel> searchResults1 = this.iOrderRepository.search(dto);
		List<OrderViewModel> searchResults2 = this.iOrderProductService.findOrdersByProductIds(dto.getProductIds());
		
		List<OrderViewModel> mergedSearchResults = new ArrayList<>();
		mergedSearchResults.addAll(searchResults1);
		mergedSearchResults.addAll(searchResults2);
		
		List<OrderViewModel> cleanedSearchResults = new ArrayList<>(new HashSet<>(mergedSearchResults));
		return cleanedSearchResults;
	}

	@Override
	public OrderModel load(Long id) {
		return this.iOrderRepository.findById(id)
				.orElseThrow(() -> new ApplicationException("Order Id Not Found"));
	}

}
