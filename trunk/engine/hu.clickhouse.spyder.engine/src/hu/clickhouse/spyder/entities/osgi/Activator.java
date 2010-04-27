package hu.clickhouse.spyder.entities.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	
	public void start(BundleContext context) throws Exception {
		System.out.println("Entities loaded");
	}
	
	public void stop(BundleContext context) throws Exception {
	}
	
}
