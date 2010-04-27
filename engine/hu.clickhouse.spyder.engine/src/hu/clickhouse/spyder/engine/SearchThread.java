package hu.clickhouse.spyder.engine;

import hu.clickhouse.spyder.browser.BrowserEngine;
import hu.clickhouse.spyder.database.DatabaseService;
import hu.clickhouse.spyder.entities.AdWord;
import hu.clickhouse.spyder.entities.HourInfo;
import hu.clickhouse.spyder.entities.SearchResult;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class SearchThread extends TimerTask {
	
	private AdWord adWord;
	private Timer timer;
	private BrowserEngine browserEngine;
	private DatabaseService databaseService;
	
	public SearchThread(AdWord adWord, int delay, BundleContext context) {
		this.adWord = adWord;
		this.timer = new Timer();
		
		// init browser engine service
		ServiceReference referenceBrowser = context.getServiceReference(BrowserEngine.class.getName());
		browserEngine = (BrowserEngine) context.getService(referenceBrowser);
		
		// init data access service
		ServiceReference referenceDataAccess = context.getServiceReference(DatabaseService.class.getName());
		databaseService = (DatabaseService) context.getService(referenceDataAccess);
		
		timer.schedule(this, delay);
	}
	
	public void run() {
		try {
			List<String> urls = browserEngine.getSearchResults(adWord.getAdWord(), adWord.getgDomain());
			
			if (urls.size() != 0) {
				for (int i = 0; i < urls.size(); i++) {
					urls.set(i, parseUrl(urls.get(i)));
				}
				store(urls);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String parseUrl(String url) {
		String regex1 = "http://";
		String regex2 = "/|&|%";
		String[] tokens1 = url.split(regex1);
		String[] tokens2 = tokens1[1].split(regex2);
		return tokens2[0];
	}
	
	@SuppressWarnings("unchecked")
	private void store(List<String> urls) {
		// get date parameters
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		Date currentDate = Calendar.getInstance().getTime();
		
		// start session
		EntityManager em = databaseService.getEntityManager();
		em.getTransaction().begin();
		
		try {
			// get stored results
			String hqlSearchResults = "SELECT result FROM SearchResult result " + "WHERE result.adWord =:adWord";
			Query querySearchResults = em.createQuery(hqlSearchResults);
			querySearchResults.setParameter("adWord", this.adWord);
			List<SearchResult> storedResults = querySearchResults.getResultList();
			
			// stored result url not in the new url list
			for (SearchResult searchResult : storedResults) {
				if (!urls.contains(searchResult.getUrl())) {
					if (searchResult.getInfoByDate(currentDate) == null) {
						HourInfo tmpInfo = new HourInfo();
						tmpInfo.setHour(currentHour);
						tmpInfo.setNumber(0);
						tmpInfo.setState(HourInfo.State.N);
						tmpInfo.setSearchResult(searchResult);
						searchResult.getInfos().add(tmpInfo);
					} else {
						HourInfo info = searchResult.getInfoByDate(currentDate);
						info.setNumber(0);
						info.setState(HourInfo.State.N);
					}
					System.out.println("Stored url not found in this hour: " + searchResult.getUrl());
				}
			}
			
			// process new urls
			for (int i = 0; i < urls.size(); i++) {
				String url = urls.get(i);
				
				// check if url is already stored
				String hql = "SELECT result FROM SearchResult result " + "WHERE result.adWord =:adWord "
						+ "AND result.url =:url";
				Query query = em.createQuery(hql);
				query.setParameter("adWord", this.adWord);
				query.setParameter("url", url);
				SearchResult storedResult = null;
				try {
					storedResult = (SearchResult) query.getSingleResult();
				} catch (Exception e) {
				}
				
				if (storedResult == null) {
					// create result
					SearchResult result = new SearchResult();
					result.setAdWord(adWord);
					result.setUrl(url);
					result.setDate(Calendar.getInstance().getTime());
					HourInfo info = new HourInfo();
					info.setHour(currentHour);
					info.setNumber(i + 1);
					info.setState(HourInfo.State.Y);
					info.setSearchResult(result);
					result.getInfos().add(info);
					
					// save result
					em.persist(result);
					System.out.println("New url found: " + result.getUrl());
				} else {
					if (storedResult.getInfoByDate(currentDate) == null) {
						HourInfo tmpInfo = new HourInfo();
						tmpInfo.setHour(currentHour);
						tmpInfo.setNumber(i);
						tmpInfo.setState(HourInfo.State.Y);
						tmpInfo.setSearchResult(storedResult);
						storedResult.getInfos().add(tmpInfo);
					} else {
						HourInfo info = storedResult.getInfoByDate(currentDate);
						info.setNumber(i);
						info.setState(HourInfo.State.Y);
					}
					System.out.println("Url updated: " + storedResult.getUrl());
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
}
