package com.hossein.PermissionTree.service.Product;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.ProductViewModel;
import com.hossein.PermissionTree.dto.product.ProductDto;
import com.hossein.PermissionTree.model.product.Product;

public interface IProductService {

	List<Product> getAllProducts();
	
	List<ProductViewModel> search(ProductDto data);
}
