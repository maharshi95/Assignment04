package com.eMart.repo;

import com.eMart.model.OrderItem;
import com.eMart.model.OrderItemID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by maharshigor on 09/07/16.
 */
public interface OrderItemRepository extends CrudRepository<OrderItem, OrderItemID> {

	@Query("select item from OrderItem item where item.orderID = ?1")
	public Iterable<OrderItem> getOrderItems(Long orderID);

}
