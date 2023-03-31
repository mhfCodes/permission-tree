package com.hossein.PermissionTree.model.permission;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSION")
public class Permission {

	@Id
	@SequenceGenerator(
				name = "seq_permission",
				sequenceName = "seq_permission",
				allocationSize = 1
			)
	@GeneratedValue(
				strategy = GenerationType.SEQUENCE,
				generator = "seq_permission"
			)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "hierarchy")
	private String hierarchy;
	
	@Column(name = "menuItem")
	private Boolean menuItem;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentPermission")
	private Permission parent;

	public Permission() {
	}

	public Permission(String name, String hierarchy, Boolean menuItem, Permission parent) {
		super();
		this.name = name;
		this.hierarchy = hierarchy;
		this.menuItem = menuItem;
		this.parent = parent;
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

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public Permission getParent() {
		return parent;
	}

	public void setParent(Permission parent) {
		this.parent = parent;
	}

	public Boolean getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(Boolean menuItem) {
		this.menuItem = menuItem;
	}

}
