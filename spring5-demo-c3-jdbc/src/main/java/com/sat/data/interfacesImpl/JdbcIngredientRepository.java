package com.sat.data.interfacesImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sat.bean.Ingredient;
import com.sat.data.interfaces.IngredientRepository;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
	
	private JdbcTemplate jdbc;
	
	//@Autowired//在Spring4.x中增加了新的特性：如果类只提供了一个带参数的构造方法，则不需要对对其内部的属性写@Autowired注解
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	@Override
	public Iterable<Ingredient> finaAll() {
		// TODO Auto-generated method stub
		return jdbc.query("select id, name, type from Ingredient", 
				this :: mapRowToIngredient);
	}

	@Override
	public Ingredient findOne(String id) {
		// TODO Auto-generated method stub
		return jdbc.queryForObject("select id ,name, type from Ingredient where id=?", 
				this :: mapRowToIngredient, id);
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		// TODO Auto-generated method stub
		jdbc.update("insert into Ingredient(id,name,type) values(?,?,?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString());
		return ingredient;
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs,int rowNum) throws SQLException {
		return new Ingredient(rs.getString("id"),
							rs.getString("name"),
							//valueOf方法是根据name查找实例
							Ingredient.Type.valueOf(rs.getString("type")));
	}

}
