package com.deppon.test03.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class PersonEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private Integer id;
	private String name;
	@Id
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	@Column(length=50,nullable=false,unique=true)
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "PersonEntity [id="+id+",name="+name+"]";
	}
}
