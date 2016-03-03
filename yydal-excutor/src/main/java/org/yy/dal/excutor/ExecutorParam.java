/*
* 文 件 名:  ExecutorParam.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  执行器参数
* 修 改 人:  zhouliang
* 修改时间:  2016年2月23日
* 修改内容:  <修改内容>
*/
package org.yy.dal.excutor;

import java.sql.Connection;
import java.util.List;

import org.yy.dal.nm.DbTable;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.route.Partition;

/**
* 执行器参数
* 
* @author  zhouliang
* @version  [1.0, 2016年2月23日]
* @since  [yy-sdal/1.0]
*/
public class ExecutorParam {
    
    //所使用的连接
    private List<Connection> conns;
    
    //sql参数值
    private List<YYDalParameter> params;
    
    //sql语句
    private String sql;
    
    //sql解析后的语句
    private Statement statement;
    
    //分表
    private DbTable table;
    
    //分库分表信息
    private Partition partition;
    
    /** 
    */
    public ExecutorParam(List<Connection> conns, List<YYDalParameter> params, String sql, Statement statement,
        DbTable table, Partition partition) {
        this.sql = sql;
        this.statement = statement;
        this.conns = conns;
        this.params = params;
        this.table = table;
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
    
    public DbTable getTable() {
        return table;
    }
    
    public void setTable(DbTable table) {
        this.table = table;
    }
    
    public Partition getPartition() {
        return partition;
    }
    
    public void setPartitions(Partition partition) {
        this.partition = partition;
    }
    
}
