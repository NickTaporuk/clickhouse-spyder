package hu.clickhouse.spyder.entities;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HOURINFO")
public class HourInfo {
	
	public static enum State {
		Y, N, X
	}
	
	private Long id;
	
	private State state;
	
	private int hour;
	
	private int number;
	
	private Date date;
	
	private SearchResult searchResult;
	
	public HourInfo() {
		state = State.X;
		number = 0;
		hour = 0;
		date = Calendar.getInstance().getTime();
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
	
	@Column(name = "STATE", nullable = false, updatable = true)
	@Enumerated(EnumType.STRING)
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	@Column(name = "HOUR", nullable = false, updatable = true)
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	@Column(name = "NUMBER", nullable = false, updatable = true)
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	@Column(name = "DATE", nullable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@ManyToOne
	@JoinColumn(name = "SEARCHRESULT_FK", nullable = true)
	public SearchResult getSearchResult() {
		return searchResult;
	}
	
	public void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}
	
	@Override
	public String toString() {
		return "HourInfo [hour=" + hour + ", id=" + id + ", number=" + number + ", searchResult=" + searchResult
				+ ", state=" + state + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + number;
		result = prime * result + ((searchResult == null) ? 0 : searchResult.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		HourInfo other = (HourInfo) obj;
		if (hour != other.hour)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (number != other.number)
			return false;
		if (searchResult == null) {
			if (other.searchResult != null)
				return false;
		} else if (!searchResult.equals(other.searchResult))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
}
