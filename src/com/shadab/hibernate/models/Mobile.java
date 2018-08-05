/**
 * 
 */
package com.shadab.hibernate.models;

import javax.persistence.*;

import javax.persistence.Entity;  
import javax.persistence.Id;  
import javax.persistence.Table; 

/**
 * @author Shadab
 *
 */


@Entity
@Table(name="mobile")
@NamedQueries({ @NamedQuery(name = "@HQL_GET_ALL_MOBILES", 
query = "from Mobile") })
@NamedNativeQueries({ @NamedNativeQuery(name = "@SQL_GET_ALL_MOBILES", 
query = "select id, mobile_name, mobile_model, mobile_price from Mobile",resultSetMapping = "updateResult") })
public class Mobile {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="mobile_name")
	private String name; 
	
	@Column(name="mobile_model")
	private String model;
	
	@Column(name="mobile_price")
	private int price; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public Mobile() {
		// TODO Auto-generated constructor stub
	}
	 public Mobile(String name, String model, int price) {
	      this.name = name;
	      this.model = model;
	      this.price = price;
	   }
		
	
}
