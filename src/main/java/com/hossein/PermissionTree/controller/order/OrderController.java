package com.hossein.PermissionTree.controller.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dto.order.OrderDto;
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
	
	@PostMapping("/search")
	@PreAuthorize("hasRole('ROLE_103')")
	public List<OrderViewModel> search(@RequestBody OrderDto dto) {
		return this.iOrderService.search(dto);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_103')")
	public OrderViewModel load(@PathVariable Long id) {
		return this.mapper.mapEToV(this.iOrderService.load(id));
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ROLE_121')")
	public long save(@RequestBody OrderDto dto) {
		return this.iOrderService.save(dto);
	}
	
	@GetMapping("/cancel/{orderId}")
	@PreAuthorize("hasRole('ROLE_104')")
	public Boolean cancel(@PathVariable Long orderId) {
		return this.iOrderService.cancelOrder(orderId);
	}
	
}
