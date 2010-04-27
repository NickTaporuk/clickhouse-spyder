package hu.clickhouse.spyder.browser.osgi;

import hu.clickhouse.spyder.browser.BrowserEngineImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Test extends TimerTask {
	
	private Timer timer;
	BrowserEngineImp browser;
	
	public Test() {
		browser = BrowserEngineImp.getInstance();
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 0, 5000);
	}
	
	@Override
	public void run() {
		try {
			List<String> list = new ArrayList<String>();
			list = browser.getSearchResults("alakformálás", "http://www.google.co.hu");
			for (String string : list) {
				System.out.println(string);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
