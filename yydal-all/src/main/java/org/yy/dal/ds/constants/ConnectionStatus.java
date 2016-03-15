/*
* 文 件 名:  ConnectionStatus.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  连接状态
* 修 改 人:  zhouliang
* 修改时间:  2016年3月2日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds.constants;

import java.util.Properties;

/**
* 记录连接状态，以便实际获取连接时重置状态。在ConnectionHolder的构造函数中重置
* 
* @author  zhouliang
* @version  [1.0, 2016年3月2日]
* @since  [yy-sdal/1.0]
*/
public class ConnectionStatus {
    
    /**
     * 自动提交事务
     */
    private Boolean autoCommit;
    
    /**
     * 只读
     */
    private Boolean readOnly = false;
    
    /**
     * 目录
     */
    private String catalog;
    
    /**
     * 事务级别
     */
    private Integer transactionIsolation;
    
    /**
     * 
     */
    private Integer holdability;
    
    private Properties clientProperties = new Properties();
    
    /**
     * 数据库schema
     */
    private String schema;
    
    public Boolean isAutoCommit() {
        return autoCommit;
    }
    
    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }
    
    public Boolean isReadOnly() {
        return readOnly;
    }
    
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
    
    public String getCatalog() {
        return catalog;
    }
    
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    
    public Integer getTransactionIsolation() {
        return transactionIsolation;
    }
    
    public void setTransactionIsolation(int transactionIsolation) {
        this.transactionIsolation = transactionIsolation;
    }
    
    public Integer getHoldability() {
        return holdability;
    }
    
    public void setHoldability(int holdability) {
        this.holdability = holdability;
    }
    
    public String getSchema() {
        return schema;
    }
    
    public void setSchema(String schema) {
        this.schema = schema;
    }
    
    public Properties getClientProperties() {
        return clientProperties;
    }
    
    public void setClientProperties(Properties clientProperties) {
        this.clientProperties = clientProperties;
    }
    
}
