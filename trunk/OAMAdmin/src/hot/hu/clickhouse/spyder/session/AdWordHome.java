package hu.clickhouse.spyder.session;

import hu.clickhouse.spyder.entities.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("adWordHome")
public class AdWordHome extends EntityHome<AdWord> {

	public void setAdWordId(Long id) {
		setId(id);
	}

	public Long getAdWordId() {
		return (Long) getId();
	}

	@Override
	protected AdWord createInstance() {
		AdWord adWord = new AdWord();
		return adWord;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public AdWord getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
