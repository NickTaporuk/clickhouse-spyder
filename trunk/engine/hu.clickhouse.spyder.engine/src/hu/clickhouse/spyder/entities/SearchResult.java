package hu.clickhouse.spyder.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SEARCHRESULT", uniqueConstraints = @UniqueConstraint(columnNames = { "URL", "ADWORD_FK" }))
public class SearchResult {
	
	private Long id;
	
	private String url;
	
	private Date date;
	
	private AdWord adWord;
	
	private List<HourInfo> infos;
	
	public SearchResult() {
		infos = new ArrayList<HourInfo>();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "URL", nullable = false, updatable = true)
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "FOUND_FIRST", nullable = false, updatable = true)
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@OneToOne
	@JoinColumn(name = "ADWORD_FK", nullable = false)
	public AdWord getAdWord() {
		return adWord;
	}
	
	public void setAdWord(AdWord adWord) {
		this.adWord = adWord;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "searchResult")
	public List<HourInfo> getInfos() {
		return infos;
	}
	
	public void setInfos(List<HourInfo> infos) {
		this.infos = infos;
	}
	
	public HourInfo getInfoByDate(Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd/k");
		String currentDate = dateFormatter.format(date);
		
		for (int i = 0; i < infos.size(); i++) {
			String storedDate = dateFormatter.format(infos.get(i).getDate());
			if (currentDate.equals(storedDate)) {
				return infos.get(i);
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "SearchResult [adWord=" + adWord + ", date=" + date + ", id=" + id + ", infos=" + infos + ", url=" + url
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adWord == null) ? 0 : adWord.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((infos == null) ? 0 : infos.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		SearchResult other = (SearchResult) obj;
		if (adWord == null) {
			if (other.adWord != null)
				return false;
		} else if (!adWord.equals(other.adWord))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (infos == null) {
			if (other.infos != null)
				return false;
		} else if (!infos.equals(other.infos))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
