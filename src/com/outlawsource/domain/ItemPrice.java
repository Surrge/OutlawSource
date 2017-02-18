package com.outlawsource.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class ItemPrice {
	private Integer Price;
	private Date createDate;  
	  
	public ItemPrice(Integer price, Date date) {
		this.Price = price;
		this.createDate = date;
	}
	public Integer getPrice() {
		return Price;
	}
	public Date getCreateDate() {  
	    return createDate;  
	} 
	
	@Override
	public boolean equals(Object obj) { 	
	   EqualsBuilder builder = new EqualsBuilder().append(this.getCreateDate(), ((ItemPrice) obj).getCreateDate());   			
	   return builder.isEquals();
	}
}
