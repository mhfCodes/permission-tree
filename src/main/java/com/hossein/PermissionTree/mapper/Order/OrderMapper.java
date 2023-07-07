package com.hossein.PermissionTree.mapper.Order;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hossein.PermissionTree.controller.viewModel.Order.OrderViewModel;
import com.hossein.PermissionTree.dto.order.OrderDto;
import com.hossein.PermissionTree.mapper.OrderProduct.OrderProductMapper;
import com.hossein.PermissionTree.mapper.User.UserMapper;
import com.hossein.PermissionTree.model.order.OrderModel;

@Mapper(
		componentModel = "spring",
		uses = {OrderProductMapper.class, UserMapper.class}
		)
public interface OrderMapper {

	@Mapping(source = "id", target = "orderId")
	@Mapping(source = "totalPrice", target = "orderTotalPrice")
	@Mapping(source = "status", target = "orderStatus")
	@Mapping(source = "description", target = "orderDescription")
	@Mapping(source = "orderDate", target = "orderDate")
	OrderViewModel mapEToV(OrderModel entity);
	
	List<OrderViewModel> mapEToVList(List<OrderModel> entityList);
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "totalPrice", target = "totalPrice")
	@Mapping(source = "status", target = "status")
	@Mapping(source = "description", target = "description")
	@Mapping(source = "orderDate", target = "orderDate")
	OrderModel mapDToE(OrderDto dto);
	
}
