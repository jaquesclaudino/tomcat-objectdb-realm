package com.objectdb.realm;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;

/**
 *
 * @author Jaques Claudino
 * Nov 24, 2016
 * http://www.christianschenk.org/blog/setup-your-own-tomcat-security-realm/
 */
public class ObjectDBRealm extends RealmBase {

    @Override
    protected String getName() {
        System.out.println("*** getName: " + this.getClass().getSimpleName());
        return this.getClass().getSimpleName();
    }

    @Override
    protected String getPassword(final String username) {
        System.out.println("*** getPassword: "+username);
        return "1";
    }
    
    @Override
    protected Principal getPrincipal(String username) {
        System.out.println("*** getPrincipal: "+username);
        final List<String> roles = new ArrayList<>();
        roles.add("admin");
        return new GenericPrincipal(username, "1", roles);
    }
    
}
