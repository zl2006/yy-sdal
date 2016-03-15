/*
* 文 件 名:  YYDalExecutor.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  SQL执行器
* 修 改 人:  zhouliang
* 修改时间:  2016年2月23日
* 修改内容:  <修改内容>
*/
package org.yy.dal.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.yy.dal.ds.YYDalStatement;
import org.yy.dal.ds.constants.StatementMode;
import org.yy.dal.route.Partition;

/**
* Sql执行器
* 
* @author  zhouliang
* @version  [1.0, 2016年2月23日]
* @since  [yy-sdal/1.0]
*/
public class YYDalStatementExecutor {
    
    public ResultSet executeQuery(YYDalExecutorParam param, YYDalStatement dalStatement)
        throws Exception {
        try {
            Partition partition = param.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = param.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                return ps.executeQuery(dalStatement.getSql());
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
                String tableName = param.getTable().getTableName();
                String tempSql = param.getSql();
                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                Connection conn = param.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                return ps.executeQuery(tempSql);
            }
            else {
                //TODO 处理多个结果集的返回
                String tableName = param.getTable().getTableName();
                List<ResultSet> results = new ArrayList<ResultSet>(); //使用分为分表的所有实例执行
                for (int i = 0; i < param.getConns().size(); ++i) {
                    for (int j = 0; j < param.getTable().getTableNum(); ++j) {
                        String tempSql = param.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        Connection conn = param.getConns().get(i);
                        Statement ps = createStatement(conn, dalStatement);
                        results.add(ps.executeQuery(tempSql));
                    }
                }
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
    
    public int executeUpdate(YYDalExecutorParam param, YYDalStatement dalStatement)
        throws Exception {
        int total = 0;
        try {
            Partition partition = param.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = param.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                total = ps.executeUpdate(dalStatement.getSql());
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
                String tableName = param.getTable().getTableName();
                String tempSql = param.getSql();
                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                Connection conn = param.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                total = ps.executeUpdate(tempSql);
            }
            else {
                String tableName = param.getTable().getTableName();
                for (int i = 0; i < param.getConns().size(); ++i) {
                    for (int j = 0; j < param.getTable().getTableNum(); ++j) {
                        String tempSql = param.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        Connection conn = param.getConns().get(i);
                        Statement ps = createStatement(conn, dalStatement);
                        total += ps.executeUpdate(tempSql);
                    }
                }
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return total;
    }
    
    public boolean execute(YYDalExecutorParam param, YYDalStatement dalStatement)
        throws Exception {
        boolean result = false;
        
        try {
            Partition partition = param.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = param.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                result = ps.execute(dalStatement.getSql());
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
                String tableName = param.getTable().getTableName();
                String tempSql = param.getSql();
                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                Connection conn = param.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                result = ps.execute(tempSql);
            }
            else {
                String tableName = param.getTable().getTableName();
                for (int i = 0; i < param.getConns().size(); ++i) {
                    for (int j = 0; j < param.getTable().getTableNum(); ++j) {
                        String tempSql = param.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        Connection conn = param.getConns().get(i);
                        Statement ps = createStatement(conn, dalStatement);
                        if (ps.execute(tempSql)) {
                            result = true;
                        }
                    }
                }
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
    
    /**
     * 根据不同模式创建Statement
     */
    private Statement createStatement(Connection conn, YYDalStatement dalStatement)
        throws SQLException {
        if (dalStatement.getModel() == StatementMode.BASE) {
            return conn.createStatement();
        }
        else if (dalStatement.getModel() == StatementMode.RESLUTSETTYPE_1) {
            return conn.createStatement(dalStatement.getReslutSetType(), dalStatement.getResultSetHoldability());
        }
        else if (dalStatement.getModel() == StatementMode.RESLUTSETTYPE_2) {
            return conn.createStatement(dalStatement.getReslutSetType(),
                dalStatement.getResultSetConcurrency(),
                dalStatement.getResultSetHoldability());
        }
        
        throw new SQLFeatureNotSupportedException();
    }
}
