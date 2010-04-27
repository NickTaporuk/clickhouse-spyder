package hu.clickhouse.spyder.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "CUSTOMER", uniqueConstraints = @UniqueConstraint(columnNames = { "COMPANY" }))
public class Customer {
	
	private Long id;
	
	private String company;
	
	private String companyUrl;
	
	private String phone;
	
	private String email;
	
	public Customer() {
	}
	
	public Customer(String company, String companyUrl, String phone,
			String email) {
		super();
		this.company = company;
		this.companyUrl = companyUrl;
		this.phone = phone;
		this.email = email;
	}
	
	public void setAdWords(List<AdWord> adWords) {
		this.adWords = adWords;
	}
	
	private List<AdWord> adWords = new ArrayList<AdWord>();
	
	public void addAdWord(AdWord adWord) {
		adWords.add(adWord);
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}
	
	@Column(name = "COMPANY", nullable = false, updatable = true)
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Column(name = "COMPANY_URL", nullable = true, updatable = true)
	public String getCompanyUrl() {
		return companyUrl;
	}
	
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	
	@Column(name = "PHONE", nullable = true, updatable = true)
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "EMAIL", nullable = true, updatable = true)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "CUSTOMER_ADWORDS", uniqueConstraints = { @UniqueConstraint(columnNames = {
			"CUSTOMER_FK", "ADWORD_FK" }) }, joinColumns = { @javax.persistence.JoinColumn(name = "CUSTOMER_FK", referencedColumnName = "ID", unique = false) }, inverseJoinColumns = { @javax.persistence.JoinColumn(name = "ADWORD_FK", referencedColumnName = "ID", unique = false) })
	public List<AdWord> getAdWords() {
		return adWords;
	}
	
	@Override
	public String toString() {
		return "Customer [adWords=" + adWords + ", company=" + company
				+ ", companyUrl=" + companyUrl + ", email=" + email + ", id="
				+ id + ", phone=" + phone + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adWords == null) ? 0 : adWords.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((companyUrl == null) ? 0 : companyUrl.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		Customer other = (Customer) obj;
		if (adWords == null) {
			if (other.adWords != null)
				return false;
		} else if (!adWords.equals(other.adWords))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (companyUrl == null) {
			if (other.companyUrl != null)
				return false;
		} else if (!companyUrl.equals(other.companyUrl))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	
}