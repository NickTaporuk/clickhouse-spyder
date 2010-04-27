package hu.clickhouse.spyder.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ADWORD", uniqueConstraints = @UniqueConstraint(columnNames = { "ADWORD", "GDOMAIN" }))
public class AdWord {
	
	private Long id;
	
	private String adWord;
	
	private String gDomain;
	
	private boolean active;
	
	private List<Customer> customers = new ArrayList<Customer>();
	
	public AdWord() {
	}
	
	public AdWord(String adWord, String gDomain, boolean active) {
		super();
		this.adWord = adWord;
		this.gDomain = gDomain;
		this.active = active;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}
	
	@Column(name = "ADWORD", nullable = false, updatable = true)
	public String getAdWord() {
		return adWord;
	}
	
	public void setAdWord(String adWord) {
		this.adWord = adWord;
	}
	
	@Column(name = "GDOMAIN", nullable = false, updatable = true)
	public String getgDomain() {
		return gDomain;
	}
	
	public void setgDomain(String gDomain) {
		this.gDomain = gDomain;
	}
	
	@Column(name = "ACTIVE", nullable = false, updatable = true)
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@ManyToMany(mappedBy = "adWords")
	public List<Customer> getCustomers() {
		return customers;
	}
	
	@Override
	public String toString() {
		return "AdWord [adWord=" + adWord + ", customers=" + customers
				+ ", gDomain=" + gDomain + ", id=" + id + ", isActive="
				+ active + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adWord == null) ? 0 : adWord.hashCode());
		result = prime * result
				+ ((customers == null) ? 0 : customers.hashCode());
		result = prime * result + ((gDomain == null) ? 0 : gDomain.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (active ? 1231 : 1237);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdWord other = (AdWord) obj;
		if (adWord == null) {
			if (other.adWord != null)
				return false;
		} else if (!adWord.equals(other.adWord))
			return false;
		if (customers == null) {
			if (other.customers != null)
				return false;
		} else if (!customers.equals(other.customers))
			return false;
		if (gDomain == null) {
			if (other.gDomain != null)
				return false;
		} else if (!gDomain.equals(other.gDomain))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (active != other.active)
			return false;
		return true;
	}
	
}
