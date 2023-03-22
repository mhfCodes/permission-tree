package com.hossein.PermissionTree.dao.repository.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hossein.PermissionTree.dao.repository.impl.Product.ProductCustomRepository;
import com.hossein.PermissionTree.model.product.Product;

public interface IProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {

}
