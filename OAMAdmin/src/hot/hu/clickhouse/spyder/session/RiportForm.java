package hu.clickhouse.spyder.session;

import hu.clickhouse.spyder.entities.AdWord;

import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

@Name("riportForm")
@Scope(ScopeType.APPLICATION)
public class RiportForm {
	@Logger
	private Log log;
	
	@In
	StatusMessages statusMessages;
	
	@In
	private EntityManager entityManager;
	
	@Out(required = false)
	private List<AdWord> list;
	
	private boolean rendered = true;
	
	private AdWord adWord;
	
	@SuppressWarnings("unchecked")
	@Factory("adWords")
	public List<AdWord> getList() {
		list = entityManager.createQuery("SELECT adword FROM AdWord adword").getResultList();
		return list;
	}
	
	public void show() {
		rendered = true;
	}
	
	public void valueChanged(ValueChangeEvent e) {
		rendered = true;
	}
	
	public boolean isRendered() {
		return rendered;
	}
	
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	
	public AdWord getAdWord() {
		return adWord;
	}
	
	public void setAdWord(AdWord adWord) {
		this.adWord = adWord;
	}
	
	public void setList(List<AdWord> list) {
		this.list = list;
	}
	
}
