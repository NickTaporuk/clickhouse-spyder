package hu.clickhouse.spyder.database;

import javax.persistence.EntityManager;

public interface DatabaseService {
	
	public EntityManager getEntityManager();
	
}
