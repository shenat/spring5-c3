package com.sat.data.interfacesImpl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.sat.bean.Ingredient;
import com.sat.bean.Taco;
import com.sat.data.interfaces.TacoRepository;


@Repository
public class JdbcTacoRepository implements TacoRepository {
	
	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	
	@Override
	public Taco save(Taco taco) {
		// TODO Auto-generated method stub
		Long tacoId = saveTacoInfo(taco);
	    taco.setId(tacoId);
	    for (Ingredient ingredient : taco.getIngredients()) {
	    	//taco和ingredient是多对多，所以要用中间表taco-ingredient维护
	    	saveIngredientToTaco(ingredient, tacoId);
	    }

	    return taco;
	}
	
	private long saveTacoInfo(Taco taco) {
	    taco.setCreatedAt(new Date());
	    PreparedStatementCreator psc =
	        new PreparedStatementCreatorFactory(
	            "insert into Taco (name, createdAt) values (?, ?)",
	            Types.VARCHAR, Types.TIMESTAMP//这两个类型为sql类型
	        ).newPreparedStatementCreator(//这边开始填充参数
	            Arrays.asList(
	                taco.getName(),
	                new Timestamp(taco.getCreatedAt().getTime())));
	    
	    //主键生成器，在创建表sql中taco表的id只设置为唯一属性，没有设置主键
	    //那到底如果表已经设置了主键还要不要夹下面这个，后面再看，推测还需要
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbc.update(psc, keyHolder);

	    return keyHolder.getKey().longValue();
	}
	
	private void saveIngredientToTaco(
	          Ingredient ingredient, long tacoId) {
	    jdbc.update(
	        "insert into Taco_Ingredients (taco, ingredient) " +
	        "values (?, ?)",
	        tacoId, ingredient.getId());
	}

}
