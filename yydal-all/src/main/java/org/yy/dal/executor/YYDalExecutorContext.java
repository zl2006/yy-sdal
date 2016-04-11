/*
* 文 件 名:  ExecutorParam.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  执行器需要的上下文
* 修 改 人:  zhouliang
* 修改时间:  2016年2月23日
* 修改内容:  <修改内容>
*/
package org.yy.dal.executor;

import java.sql.Connection;
import java.util.List;

import org.yy.dal.ds.YYDalParameter;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.route.Partition;

/**
* 执行器需要的上下文
* 
* @author  zhouliang
* @version  [1.0, 2016年2月23日]
* @since  [yy-sdal/1.0]
*/
public class YYDalExecutorContext {
    
    //所使用的连接
    private List<Connection> conns;
    
    //sql语句
    private String sql;
    
    //sql解析后的语句
    private Statement statement;
    
    //sql参数值
    private List<YYDalParameter> params;
    
    //分库分表信息
    private Partition partition;
    
    /** 
    */
    public YYDalExecutorContext(List<Connection> conns, String sql, Statement statement, List<YYDalParameter> params,
        Partition partition) {
        this.sql = sql;
        this.statement = statement;
        this.conns = conns;
        this.params = params;
        this.partition = partition;
    }
    
    public String getSql() {
        return sql;
    }
    
    public void setSql(String sql) {
        this.sql = sql;
    }
    
    public Statement getStatement() {
        return statement;
    }
    
    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    public List<Connection> getConns() {
        return conns;
    }
    
    public void setConns(List<Connection> conns) {
        this.conns = conns;
    }
    
    public List<YYDalParameter> getParams() {
        return params;
    }
    
    public void setParams(List<YYDalParameter> params) {
        this.params = params;
    }
    
    public Partition getPartition() {
        return partition;
    }
    
    public void setPartitions(Partition partition) {
        this.partition = partition;
    }
    
}
