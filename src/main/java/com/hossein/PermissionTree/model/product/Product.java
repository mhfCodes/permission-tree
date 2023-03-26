package com.hossein.PermissionTree.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT")
public class Product {

	@Id
	@SequenceGenerator(
				name = "seq_product",
				sequenceName = "seq_product",
				allocationSize = 1
			)
	@GeneratedValue(
				strategy = GenerationType.SEQUENCE,
				generator = "seq_product"
			)
	private Long id;
	
	@Column(name = "name", length = 50)
	private String name;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "count")
	private Integer count;
	
	@Column(name = "dateAdded", length = 50)
	private String dateAdded;
	
	@Column(name = "dateModified")
	private String dateModified;
	
	public Product() {
	}

	public Product(String name, Double price, Integer count, String dateAdded) {
		this.name = name;
		this.price = price;
		this.count = count;
		this.dateAdded = dateAdded;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	
}
