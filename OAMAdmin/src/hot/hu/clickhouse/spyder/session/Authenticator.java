package hu.clickhouse.spyder.session;

import hu.clickhouse.spyder.entities.Role;
import hu.clickhouse.spyder.entities.User;
import hu.clickhouse.spyder.security.PasswordManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

@Name("authenticator")
public class Authenticator {
	@Logger
	private Log log;
	
	@In
	private EntityManager entityManager;
	
	@In
	Identity identity;
	
	@In
	Credentials credentials;
	
	@In(create = true)
	private PasswordManager passwordManager;
	
	public boolean authenticate() {
		log.info("authenticating {0}", credentials.getUsername());
		
		try {
			User user = (User) entityManager.createQuery("SELECT user FROM User user WHERE name = :name").setParameter(
					"name", credentials.getUsername()).getSingleResult();
			
			if (!validatePassword(credentials.getPassword(), user)) {
				return false;
			}
			identity.addRole("member");
			if (user.getRoles() != null) {
				for (Role role : user.getRoles()) {
					identity.addRole(role.getName());
				}
			}
			
			return true;
		} catch (NoResultException e) {
			return false;
		}
		
	}
	
	public boolean validatePassword(String password, User u) {
		return passwordManager.hash(password).equals(u.getPasswordHash());
	}
	
}
