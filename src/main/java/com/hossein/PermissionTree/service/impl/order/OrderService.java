package com.hossein.PermissionTree.service.impl.order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dao.repository.order.IOrderRepository;
import com.hossein.PermissionTree.dto.order.OrderDto;
import com.hossein.PermissionTree.exception.ApplicationException;
import com.hossein.PermissionTree.mapper.Order.OrderMapper;
import com.hossein.PermissionTree.mapper.OrderProduct.OrderProductMapper;
import com.hossein.PermissionTree.model.order.OrderModel;
import com.hossein.PermissionTree.model.order.OrderProduct;
import com.hossein.PermissionTree.service.order.IOrderService;
import com.hossein.PermissionTree.service.orderProduct.IOrderProductService;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private IOrderRepository iOrderRepository;
	
	@Autowired
	private IOrderProductService iOrderProductService;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderProductMapper orderProductMapper;
	
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

	@Override
	@Transactional
	public long save(OrderDto dto) {
		
		OrderModel order = this.iOrderRepository.save(this.orderMapper.mapDToE(dto));
		
		Set<OrderProduct> orderProducts = this.orderProductMapper.mapDToEList(dto.getOrderProducts());
		orderProducts.forEach(orderProduct -> {
			orderProduct.setOrder(order);
		});
		this.iOrderProductService.saveAll(orderProducts);
		
		return order.getId();
	}

}
