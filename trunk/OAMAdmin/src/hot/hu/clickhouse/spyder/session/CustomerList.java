package hu.clickhouse.spyder.session;

import hu.clickhouse.spyder.entities.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("customerList")
public class CustomerList extends EntityQuery<Customer> {

	private static final String EJBQL = "select customer from Customer customer";

	private static final String[] RESTRICTIONS = {
			"lower(customer.company) like lower(concat(#{customerList.customer.company},'%'))",
			"lower(customer.companyUrl) like lower(concat(#{customerList.customer.companyUrl},'%'))",
			"lower(customer.email) like lower(concat(#{customerList.customer.email},'%'))",
			"lower(customer.phone) like lower(concat(#{customerList.customer.phone},'%'))", };

	private Customer customer = new Customer();

	public CustomerList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Customer getCustomer() {
		return customer;
	}
}
