package com.hossein.PermissionTree.controller.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.mapper.Order.OrderMapper;
import com.hossein.PermissionTree.service.order.IOrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private IOrderService iOrderService;
	
	@Autowired
	private OrderMapper mapper;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_103')")
	public List<OrderViewModel> getAll() {
		return this.mapper.mapEToVList(this.iOrderService.getAll());
	}
	
	
}
