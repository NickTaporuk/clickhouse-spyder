package hu.clickhouse.spyder.session;

import hu.clickhouse.spyder.entities.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("customerHome")
public class CustomerHome extends EntityHome<Customer> {

	public void setCustomerId(Long id) {
		setId(id);
	}

	public Long getCustomerId() {
		return (Long) getId();
	}

	@Override
	protected Customer createInstance() {
		Customer customer = new Customer();
		return customer;
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

	public Customer getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
