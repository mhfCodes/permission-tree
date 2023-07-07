package com.hossein.PermissionTree.mapper.OrderProduct;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hossein.PermissionTree.controller.viewModel.OrderProduct.OrderProductViewModel;
import com.hossein.PermissionTree.dto.orderProduct.OrderProductDto;
import com.hossein.PermissionTree.mapper.Product.ProductMapper;
import com.hossein.PermissionTree.model.order.OrderProduct;

@Mapper(
		componentModel = "spring",
		uses = ProductMapper.class
		)
public interface OrderProductMapper {

	@Mapping(source = "id", target = "orderProductId")
	@Mapping(source = "price", target = "orderProductPrice")
	@Mapping(source = "count", target = "orderProductCount")
	@Mapping(source = "totalPrice", target = "orderProductTotalPrice")
	OrderProductViewModel mapEToV(OrderProduct entity);
	
	Set<OrderProductViewModel> mapEToVList(Set<OrderProduct> entityList);
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "price", target = "price")
	@Mapping(source = "count", target = "count")
	@Mapping(source = "totalPrice", target = "totalPrice")
	OrderProduct mapDToE(OrderProductDto dto);
	
	Set<OrderProduct> mapDToEList(Set<OrderProductDto> dtoList);
	
}
