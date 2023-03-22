package com.hossein.PermissionTree.dao.repository.impl.Product;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.ProductViewModel;
import com.hossein.PermissionTree.dto.product.ProductDto;

public interface ProductCustomRepository {

	List<ProductViewModel> getAll(ProductDto data);
}
