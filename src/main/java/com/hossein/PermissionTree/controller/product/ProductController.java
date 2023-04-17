package com.hossein.PermissionTree.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.controller.viewModel.Product.ProductViewModel;
import com.hossein.PermissionTree.dto.product.ProductDto;
import com.hossein.PermissionTree.mapper.Product.ProductMapper;
import com.hossein.PermissionTree.service.Product.IProductService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {

	@Autowired
	private ProductMapper mapper;
	@Autowired
	private IProductService iProductService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_3')")
	public List<ProductViewModel> getAll() {
		return this.mapper.mapEtoVList(this.iProductService.getAllProducts());
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_3')")
	public ProductViewModel load(@PathVariable Long id) {
		return this.mapper.mapEtoV(this.iProductService.load(id));
	}
	
	@PostMapping("/search")
	@PreAuthorize("hasRole('ROLE_3')")
	public List<ProductViewModel> search(@RequestBody ProductDto data) {
		return this.iProductService.search(data);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_2', 'ROLE_4')")
	public long save(@RequestBody ProductDto dto) {
		return this.iProductService.save(this.mapper.mapDtoE(dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_5')")
	public Boolean delete(@PathVariable Long id) {
		return this.iProductService.delete(id);
	}
	
}
