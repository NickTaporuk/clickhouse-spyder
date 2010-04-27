package hu.clickhouse.spyder.engine;

import hu.clickhouse.spyder.database.DatabaseService;
import hu.clickhouse.spyder.entities.AdWord;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class SpyderEngine extends TimerTask {
	
	private Timer timer;
	private DatabaseService databaseService;
	private BundleContext context;
	// public static final int PERIOD = 20 * 60 * 1000;
	
	public static final int PERIOD = 90000;
	
	public SpyderEngine(BundleContext context) {
		System.out.println("Spyder engine started");
		
		// init database service
		ServiceReference reference = context.getServiceReference(DatabaseService.class.getName());
		databaseService = (DatabaseService) context.getService(reference);
		
		this.context = context;
		
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 5000, PERIOD);
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		System.out.println();
		System.out.println("New round: " + Calendar.getInstance().getTime());
		
		EntityManager em = databaseService.getEntityManager();
		
		try {
			String hql = "SELECT adword FROM AdWord adword WHERE adword.active = true";
			Query query = em.createQuery(hql);
			List<AdWord> adWords = query.getResultList();
			if (adWords.size() == 0) {
				return;
			}
			
			Float delayf = new Float(PERIOD / adWords.size());
			int delay = delayf.intValue();
			
			for (int i = 0; i < adWords.size(); i++) {
				new SearchThread(adWords.get(i), delay * i, context);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
	}
}
