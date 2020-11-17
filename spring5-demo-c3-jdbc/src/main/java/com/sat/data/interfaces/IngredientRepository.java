package com.sat.data.interfaces;

import com.sat.bean.Ingredient;



public interface IngredientRepository {
	
	Iterable<Ingredient> finaAll();
	
	Ingredient findOne(String id);
	
	Ingredient save(Ingredient ingredient);
}
