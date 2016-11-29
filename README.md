- copy lib/objectdb-jee.jar to apache-tomee-plus-7.0.2/lib

- copy lib/tomcat-objectdb-realm-0.0.1.jar to apache-tomee-plus-7.0.2/lib

- edit apache-tomee-plus-7.0.2/conf/server.xml:
    <Realm className="org.apache.catalina.realm.LockOutRealm 
        ...
        <Realm className="com.objectdb.realm.ObjectDBRealm" 
             resourceName="app-realm" 
             url="$objectdb/db/app.odb"
             userEntity="User" 
             userNameColumn="login" 
             passwordColumn="password"
             groupNameColumn="groupName"/>