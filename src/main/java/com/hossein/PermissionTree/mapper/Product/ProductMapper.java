package com.hossein.PermissionTree.mapper.Product;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hossein.PermissionTree.controller.viewModel.Product.ProductViewModel;
import com.hossein.PermissionTree.dto.product.ProductDto;
import com.hossein.PermissionTree.model.product.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
	Product mapDtoE(ProductDto dto);
	
	@Mapping(source = "id", target = "productId")
	@Mapping(source = "name", target = "productName")
	@Mapping(source = "price", target = "productPrice")
	@Mapping(source = "count", target = "productCount")
	@Mapping(source = "dateAdded", target = "productDateAdded")
	@Mapping(source = "dateModified", target = "productDateModified")
	ProductViewModel mapEtoV(Product entity);
	
	List<ProductViewModel> mapEtoVList(List<Product> entityList);
	
}
