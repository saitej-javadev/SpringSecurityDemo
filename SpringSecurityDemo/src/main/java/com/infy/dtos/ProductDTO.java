package com.infy.dtos;

import java.math.BigDecimal;

public class ProductDTO {
	
	private Long id;
	private String name;
	private BigDecimal price;
	
	public ProductDTO() {
		
	}

	public ProductDTO(Long id, String name, BigDecimal price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	

}
