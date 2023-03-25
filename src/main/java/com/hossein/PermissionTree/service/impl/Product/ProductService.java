package com.hossein.PermissionTree.service.impl.Product;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.controller.viewModel.ProductViewModel;
import com.hossein.PermissionTree.dao.repository.Product.IProductRepository;
import com.hossein.PermissionTree.dto.product.ProductDto;
import com.hossein.PermissionTree.model.product.Product;
import com.hossein.PermissionTree.service.Product.IProductService;

@Service
public class ProductService implements IProductService {
	
	@Autowired
	private IProductRepository iProductRepository;

	@Override
	public List<Product> getAllProducts() {
		return this.iProductRepository.findAll();
	}
	
	@Override
	public Product load(Long productId) {
		return this.iProductRepository.findById(productId).orElse(null);
	}

	@Override
	public List<ProductViewModel> search(ProductDto data) {
		return this.iProductRepository.getAll(data);
	}

	@Override
	@Transactional
	public long save(Product entity) {
		return this.iProductRepository.save(entity).getId();
	}

	@Override
	@Transactional
	public Boolean delete(Long productId) {
		this.iProductRepository.deleteById(productId);
		return (this.load(productId) == null) ? true : false;
	}

}
