package com.hossein.PermissionTree.dao.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hossein.PermissionTree.dao.repository.impl.order.OrderCustomRepository;
import com.hossein.PermissionTree.model.order.OrderModel;

public interface IOrderRepository extends JpaRepository<OrderModel, Long>, OrderCustomRepository {

}
