package com.objectdb.realm;

import java.security.Principal;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;

/**
 *
 * @author Jaques Claudino
 * Nov 24, 2016
 * http://www.christianschenk.org/blog/setup-your-own-tomcat-security-realm/
 */
public class ObjectDBRealm extends RealmBase {

    private static final Logger LOG = Logger.getLogger(ObjectDBRealm.class.getName());
    
    private String url = "url";
    private String userEntity = "User";
    private String userNameColumn = "login";
    private String passwordColumn = "password";
    private String groupNameColumn = "groupName";
    
    @Override
    protected String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected String getPassword(final String username) {
        return null;
    }
    
    @Override
    protected Principal getPrincipal(String username) {
        return null;
    }

    @Override
    public Principal authenticate(String user, String password) {
        LOG.log(Level.INFO, "Url: {0}", url);
        LOG.log(Level.INFO, "Login: {0}", user);
        if (user == null || password == null) {
            return null;
        }        
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(url);
        EntityManager em = emf.createEntityManager();
        
        try {
            String query = String.format("select u.%s from %s u where u.%s=?1 and u.%s=?2", groupNameColumn, userEntity, userNameColumn, passwordColumn);
            LOG.log(Level.INFO, "Query: {0}", query);
            
            String groupName = em.createQuery(query, String.class)
                .setParameter(1, user)
                .setParameter(2, password)
                .getSingleResult();
            
            if (groupName != null) {
                LOG.log(Level.INFO, "Group: {0}", groupName);
                return new GenericPrincipal(user, password, Arrays.asList(groupName));
            }            
        } catch (NoResultException ex) {
            LOG.log(Level.WARNING, "Error on authenticate", ex);
        } finally {
            em.close();
            emf.close();        
        }        
        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(String userEntity) {
        this.userEntity = userEntity;
    }

    public String getUserNameColumn() {
        return userNameColumn;
    }

    public void setUserNameColumn(String userNameColumn) {
        this.userNameColumn = userNameColumn;
    }

    public String getPasswordColumn() {
        return passwordColumn;
    }

    public void setPasswordColumn(String passwordColumn) {
        this.passwordColumn = passwordColumn;
    }    

    public String getGroupNameColumn() {
        return groupNameColumn;
    }

    public void setGroupNameColumn(String groupNameColumn) {
        this.groupNameColumn = groupNameColumn;
    }
    
}
