package com.hossein.PermissionTree.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.controller.viewModel.ProductViewModel;
import com.hossein.PermissionTree.dto.product.ProductDto;
import com.hossein.PermissionTree.mapper.Product.ProductMapper;
import com.hossein.PermissionTree.service.Product.IProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductMapper mapper;
	@Autowired
	private IProductService iProductService;
	
	@GetMapping("/product")
	public List<ProductViewModel> getAll() {
		return this.mapper.mapEtoVList(this.iProductService.getAllProducts());
	}
	
	@PostMapping("/product/search")
	public List<ProductViewModel> search(@RequestBody ProductDto data) {
		return this.iProductService.search(data);
	}
	
	
}
