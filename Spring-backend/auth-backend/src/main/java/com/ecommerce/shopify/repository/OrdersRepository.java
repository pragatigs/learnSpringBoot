package com.ecommerce.shopify.repository;

import com.ecommerce.shopify.model.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Orders findByOrderId(String orderId);
    List<Orders> findByUsername(String username);
}
