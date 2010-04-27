package hu.clickhouse.spyder.browser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.webrenderer.server.BrowserFactory;
import com.webrenderer.swing.IBrowserCanvas;
import com.webrenderer.swing.dom.IDocument;
import com.webrenderer.swing.dom.IElement;
import com.webrenderer.swing.dom.IElementCollection;
import com.webrenderer.swing.event.NetworkEvent;
import com.webrenderer.swing.event.NetworkListener;

public class BrowserEngineImp implements BrowserEngine {
	
	private static BrowserEngineImp instance;
	private IBrowserCanvas browser;
	private List<String> urls;
	private boolean isReloaded;
	private String adWord;
	
	private final Object lock = new Object();
	private boolean isReady;
	
	NetworkListener listener = new NetworkListener() {
		
		@Override
		public void onProgressChange(NetworkEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onNetworkStatus(NetworkEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onNetworkError(NetworkEvent arg0) {
			isReady = true;
		}
		
		@Override
		public void onHTTPResponse(NetworkEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onHTTPInterceptHeaders(NetworkEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onDocumentLoad(NetworkEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onDocumentComplete(NetworkEvent arg0) {
			synchronized (lock) {
				IDocument page = browser.getDocument();
				IElementCollection tags = page.getAll();
				
				// Do the search
				if (!isReloaded) {
					isReloaded = true;
					IElement button = null;
					IElement searchbox = null;
					
					for (int i = 0; i < tags.length(); i++) {
						IElement tag = tags.item(i);
						if (tag.getAttribute("name", 0).equals("q")) {
							searchbox = tag;
						}
						if (tag.getAttribute("name", 0).equals("btnG")) {
							button = tag;
						}
					}
					
					if (searchbox != null && button != null) {
						searchbox.setValue(adWord);
						button.click();
					}
				}
				// Parse results
				else {
					for (int i = 0; i < tags.length(); i++) {
						IElement tag = tags.item(i);
						String tagId = tag.getAttribute("id", 0);
						if (!tagId.equals("")) {
							Pattern classNamePattern = Pattern
									.compile("(pa\\d)|(an\\d)");
							Matcher m = classNamePattern.matcher(tagId);
							if (m.matches()) {
								urls.add(tag.getAttribute("href", 0));
							}
						}
					}
					isReady = true;
					lock.notifyAll();
				}
			}
			
		}
	};
	
	private BrowserEngineImp() {
		BrowserFactory.setLicenseData("30dtrial", "LHNJA01OIV14PD33DEA067K8");
		browser = BrowserFactory.spawnHeadlessMozilla();
		browser.addNetworkListener(listener);
	}
	
	public static BrowserEngineImp getInstance() {
		if (instance == null) {
			instance = new BrowserEngineImp();
		}
		return instance;
	}
	
	public synchronized List<String> getSearchResults(String adWord,
			String gDomain) throws Exception {
		
		try {
			isReloaded = false;
			isReady = false;
			urls = new ArrayList<String>();
			this.adWord = adWord;
			browser.loadURL(gDomain);
			
			synchronized (lock) {
				while (!isReady) {
					lock.wait();
				}
				return urls;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return urls;
		}
		
	}
	
	public void destroy() {
		BrowserFactory.destroyBrowser(browser);
		BrowserFactory.shutdownMozilla();
	}
	
	public IBrowserCanvas getBrowser() {
		return browser;
	}
	
}
