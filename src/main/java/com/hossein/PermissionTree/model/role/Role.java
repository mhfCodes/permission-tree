package com.hossein.PermissionTree.model.role;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hossein.PermissionTree.model.permission.Permission;

@Entity
@Table(name = "ROLE_TABLE")
public class Role {

	@Id
	@SequenceGenerator(
				name = "seq_role",
				sequenceName = "seq_role",
				allocationSize = 1
			)
	@GeneratedValue(
				strategy = GenerationType.SEQUENCE,
				generator = "seq_role"
			)
	private Long id;
	
	@Column(name = "role", length = 50)
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
				name = "ROLE_PERMISSION",
				joinColumns = {@JoinColumn(name = "roleId")},
				inverseJoinColumns = {@JoinColumn(name = "permissionId")}
			)
	private Set<Permission> permissions;
	
	@Transient
	private String selected;

	public Role() {
	}

	public Role(String name, Set<Permission> permissions) {
		this.name = name;
		this.permissions = permissions;
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

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	
}
