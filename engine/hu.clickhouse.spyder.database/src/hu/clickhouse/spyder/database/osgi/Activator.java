package hu.clickhouse.spyder.database.osgi;

import hu.clickhouse.spyder.database.DatabaseService;
import hu.clickhouse.spyder.database.DatabaseServiceImp;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	
	private DatabaseService databaseService;
	
	public void start(BundleContext context) throws Exception {
		databaseService = DatabaseServiceImp.getInstance();
		context.registerService(DatabaseService.class.getName(), databaseService, null);
		System.out.println("Database service is registered");
	}
	
	public void stop(BundleContext context) throws Exception {
	}
	
}
