package com.hossein.PermissionTree.model.permission;

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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentPermission")
	private Permission parent;

	public Permission() {
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

}
