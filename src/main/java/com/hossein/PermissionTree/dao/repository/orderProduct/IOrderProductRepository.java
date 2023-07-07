package com.hossein.PermissionTree.dao.repository.orderProduct;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hossein.PermissionTree.dao.repository.impl.orderProduct.OrderProductCustomRepository;
import com.hossein.PermissionTree.model.order.OrderProduct;

public interface IOrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductCustomRepository {

}
