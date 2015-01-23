package org.yy.dal.parse.schema;

/**
 * 
 * 数据库对象, 例如server.databaseName
 * 
 * @author  zhouliang
 * @version  [0.1, 2015年1月22日]
 * @since  [yydal-parse/0.1]
 */
public final class Database implements MultiPartName {
    
    /**
     * 服务名
     */
    private Server server;
    
    /**
     * 数据库名
     */
    private String databaseName;
    
    public Database(String databaseName) {
        setDatabaseName(databaseName);
    }
    
    public Database(Server server, String databaseName) {
        setServer(server);
        setDatabaseName(databaseName);
    }
    
    public Server getServer() {
        return server;
    }
    
    public void setServer(Server server) {
        this.server = server;
    }
    
    public String getDatabaseName() {
        return databaseName;
    }
    
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    
    @Override
    public String getFullyQualifiedName() {
        String fqn = "";
        
        if (server != null) {
            fqn += server.getFullyQualifiedName();
        }
        if (!fqn.isEmpty()) {
            fqn += ".";
        }
        
        if (databaseName != null) {
            fqn += databaseName;
        }
        
        return fqn;
    }
    
    @Override
    public String toString() {
        return getFullyQualifiedName();
    }
}
