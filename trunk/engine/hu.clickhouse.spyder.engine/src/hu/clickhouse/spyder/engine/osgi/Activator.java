package hu.clickhouse.spyder.engine.osgi;

import hu.clickhouse.spyder.engine.SpyderEngine;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	
	public void start(BundleContext context) throws Exception {
		System.out.println("Spyder started");
		
		// Start SpyderEngine
		new SpyderEngine(context);
		
	}
	
	public void stop(BundleContext context) throws Exception {
		System.out.println("Spyder ended");
	}
	
}
