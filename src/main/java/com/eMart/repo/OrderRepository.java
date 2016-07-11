package com.eMart.repo;

import com.eMart.model.Order;
import com.eMart.model.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by maharshigor on 08/07/16.
 */
public interface OrderRepository  extends CrudRepository<Order,Long> {

	@Query("select item from OrderItem item where item.orderID = ?1")
	public Iterable<OrderItem> getOrderItems(Long orderID);
}
