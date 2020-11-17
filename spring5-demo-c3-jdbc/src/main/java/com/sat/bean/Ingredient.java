package com.sat.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data//用的lombok的自动生成缺少方法,先不使用
@RequiredArgsConstructor//用的lombok的自动生成参数构造器,先不使用
public class Ingredient {
	
	/**
	 * 使用final修饰会报：The blank final field id may not have been  initialized
	 * 但是如果构造器中对其初始化了，则不会报这个错，且不能有无参构造器了
	 * 因为无参构造器会初始化的时候不将final变量初始化
	 */
	private final String id;
	private final String name;
	private final Type type;
  
 	public static enum Type {
 		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
 	}

 	

 	
 	
  
  
}
