/*
* 文 件 名:  ConnectionStatus.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  连接状态
* 修 改 人:  zhouliang
* 修改时间:  2016年3月2日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds;

/**
* 连接状态
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
     * 是否关闭
     */
    private Boolean close = false;
    
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
    
    private String schema;
    
    /**
    * @return 返回 autoCommit
    */
    public Boolean isAutoCommit() {
        return autoCommit;
    }
    
    /**
    * @param 对autoCommit进行赋值
    */
    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }
    
    /**
    * @return 返回 close
    */
    public Boolean isClose() {
        return close;
    }
    
    /**
    * @param 对close进行赋值
    */
    public void setClose(boolean close) {
        this.close = close;
    }
    
    /**
    * @return 返回 readOnly
    */
    public Boolean isReadOnly() {
        return readOnly;
    }
    
    /**
    * @param 对readOnly进行赋值
    */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
    
    /**
    * @return 返回 catalog
    */
    public String getCatalog() {
        return catalog;
    }
    
    /**
    * @param 对catalog进行赋值
    */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    
    /**
    * @return 返回 transactionIsolation
    */
    public Integer getTransactionIsolation() {
        return transactionIsolation;
    }
    
    /**
    * @param 对transactionIsolation进行赋值
    */
    public void setTransactionIsolation(int transactionIsolation) {
        this.transactionIsolation = transactionIsolation;
    }
    
    /**
    * @return 返回 holdability
    */
    public Integer getHoldability() {
        return holdability;
    }
    
    /**
    * @param 对holdability进行赋值
    */
    public void setHoldability(int holdability) {
        this.holdability = holdability;
    }
    
    /**
    * @return 返回 schema
    */
    public String getSchema() {
        return schema;
    }
    
    /**
    * @param 对schema进行赋值
    */
    public void setSchema(String schema) {
        this.schema = schema;
    }
    
}
