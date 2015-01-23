package org.yy.dal.parse.schema;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库服务器
 * 
 * @author  zhouliang
 * @version  [0.1, 2015年1月22日]
 * @since  [yydal-parse/0.1]
 */
public final class Server implements MultiPartName {
    public static final Pattern SERVER_PATTERN = Pattern.compile("\\[([^\\]]+?)(?:\\\\([^\\]]+))?\\]");
    
    /**
     * 服务器名
     */
    private String serverName;
    
    /**
     * 实例名
     */
    private String instanceName;
    
    public Server(String serverAndInstanceName) {
        if (serverAndInstanceName != null) {
            final Matcher matcher = SERVER_PATTERN.matcher(serverAndInstanceName);
            if (!matcher.find()) {
                throw new IllegalArgumentException(String.format("%s is not a valid database reference",
                    serverAndInstanceName));
            }
            setServerName(matcher.group(1));
            setInstanceName(matcher.group(2));
        }
    }
    
    public Server(String serverName, String instanceName) {
        setServerName(serverName);
        setInstanceName(instanceName);
    }
    
    public String getServerName() {
        return serverName;
    }
    
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    
    public String getInstanceName() {
        return instanceName;
    }
    
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
    
    @Override
    public String getFullyQualifiedName() {
        if (serverName != null && !serverName.isEmpty() && instanceName != null && !instanceName.isEmpty()) {
            return String.format("[%s\\%s]", serverName, instanceName);
        }
        else if (serverName != null && !serverName.isEmpty()) {
            return String.format("[%s]", serverName);
        }
        else {
            return "";
        }
    }
    
    @Override
    public String toString() {
        return getFullyQualifiedName();
    }
}
