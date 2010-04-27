package hu.clickhouse.spyder.browser.osgi;

import hu.clickhouse.spyder.browser.BrowserEngine;
import hu.clickhouse.spyder.browser.BrowserEngineImp;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	
	private BrowserEngine service;
	
	public void start(BundleContext context) throws Exception {
		service = BrowserEngineImp.getInstance();
		context.registerService(BrowserEngine.class.getName(), service, null);
		System.out.println("Spyder browser engine is registered");
		
		// JFrame frame = new JFrame("Simple Browser Example");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		
		// Create and append the browser
		// JPanel panel = new JPanel(new BorderLayout());
		// panel.add(service.getBrowser().getComponent());
		// frame.setContentPane(panel);
		//		
		// frame.setSize(640, 480);
		// frame.setVisible(true);
	}
	
	public void stop(BundleContext context) throws Exception {
		service.destroy();
		System.out.println("Spyder browser engine is unregistered");
	}
	
}
