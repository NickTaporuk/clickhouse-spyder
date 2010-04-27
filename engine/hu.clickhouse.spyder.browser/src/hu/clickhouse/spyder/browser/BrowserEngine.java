package hu.clickhouse.spyder.browser;

import java.util.List;

import com.webrenderer.swing.IBrowserCanvas;

public interface BrowserEngine {
	
	public List<String> getSearchResults(String adWord, String gDomain)
			throws Exception;
	
	public void destroy();
	
	public IBrowserCanvas getBrowser();
	
}
