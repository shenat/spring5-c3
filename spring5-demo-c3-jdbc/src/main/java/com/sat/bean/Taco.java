package com.sat.bean;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

//墨西哥卷
@Data
public class Taco {

  @NotNull
  @Size(min=5, message="Name must be at least 5 characters long")
  private String name;
  
  @NotNull(message="You must choose at least 1 ingredient")//在校验的时候必须加上这个，不然null的时候下面的size校验不会生效
  @Size(min=1, message="You must choose at least 1 ingredient")
  private List<Ingredient> ingredients;
  
  private Long id;
  
  private Date createdAt;
	
	

}
