package com.sat.data.interfacesImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sat.bean.Order;
import com.sat.bean.Taco;
import com.sat.data.interfaces.OrderRepository;

@Repository
public class JdbcOrderRepository implements OrderRepository {
	
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;
	
	//可以不用Autowired，SimpleJdbcInsert是对JdbcTemplate的包装
	//可以更容易的插入数据，而不需要PreparedStatementCreator和keyHolder
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order")
				.usingGeneratedKeyColumns("id");
		this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order_Tacos");
		this.objectMapper = new ObjectMapper();
	}
	

	@Override
	public Order save(Order order) {
		// TODO Auto-generated method stub
		order.setPlacedAt(new Date());
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		List<Taco> tacos = order.getTacos();
		for(Taco taco : tacos) {
			saveTacoToOrder(taco, orderId);
		}
		
		return order;
	}
	
	private long saveOrderDetails(Order order) {
		//将第一参数的值转为第二种参数类型的值，类似与先转为json，再将json转为第二种类型
		Map<String,Object> values = objectMapper.convertValue(order, Map.class);
		//加这个是因为，默认转换为map的时候date会转为long类型，这边重新给map中的palcedAt赋值会替换为date类型
		values.put("placedAt", order.getPlacedAt());
		
		long orderId = orderInserter.executeAndReturnKey(values)
				.longValue();
		return orderId;
	}
	
	private void saveTacoToOrder(Taco taco,long orderId) {
		Map<String,Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacoInserter.execute(values);
		
	}


}
