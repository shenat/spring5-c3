package com.sat.data.interfaces;

import com.sat.bean.Order;

public interface OrderRepository {

	Order save(Order order);
}
