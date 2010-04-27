package hu.clickhouse.spyder.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@SuppressWarnings("serial")
@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class User implements Serializable {
	
	private Long id;
	
	private String name;
	
	private String passwordHash;;
	
	private Set<Role> roles = new HashSet<Role>();
	
	@Id
	@GeneratedValue
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
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MEMBER_ROLE", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return this.roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
