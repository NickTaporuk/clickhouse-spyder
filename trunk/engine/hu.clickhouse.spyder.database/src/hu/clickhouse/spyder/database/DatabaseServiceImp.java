package hu.clickhouse.spyder.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseServiceImp implements DatabaseService {
	
	private static DatabaseServiceImp instance;
	EntityManagerFactory emf;
	
	private DatabaseServiceImp() {
		emf = Persistence
				.createEntityManagerFactory("hu.clickhouse.spyder.jpa");
	}
	
	public static DatabaseServiceImp getInstance() {
		if (instance == null) {
			instance = new DatabaseServiceImp();
		}
		return instance;
	}
	
	public EntityManager getEntityManager() {
		EntityManager em = emf.createEntityManager();
		return em;
	}
	
}
