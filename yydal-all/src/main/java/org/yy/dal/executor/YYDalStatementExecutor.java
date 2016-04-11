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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.yy.dal.ds.YYDalStatement;
import org.yy.dal.route.Partition;

/**
* Sql执行器
* 
* @author  zhouliang
* @version  [1.0, 2016年2月23日]
* @since  [yy-sdal/1.0]
*/
public class YYDalStatementExecutor extends AbsYYDalExecutor {
    
    public ResultSet executeQuery(YYDalExecutorContext executorCtx, YYDalStatement dalStatement)
        throws Exception {
        try {
            Partition partition = executorCtx.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = executorCtx.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                return ps.executeQuery(dalStatement.getSql());
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
//                String tableName = executorCtx.getTable().getTableName();
//                String tempSql = executorCtx.getSql();
//                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                String tableName = partition.getTable().getName();
                partition.getTable().setName(tableName + "_" + partition.getTableNumber());
                String tempSql = executorCtx.getStatement().toString();
                
                Connection conn = executorCtx.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                return ps.executeQuery(tempSql);
            }
            else {
                //TODO 处理多个结果集的返回
//                String tableName = executorCtx.getTable().getTableName();
                String tableName = partition.getTable().getName();
                
                List<ResultSet> results = new ArrayList<ResultSet>(); //使用分为分表的所有实例执行
                for (int i = 0; i < executorCtx.getConns().size(); ++i) {
//                    for (int j = 0; j < executorCtx.getTable().getTableNum(); ++j) {
                      for (int j = 0; j < partition.getTableRule().getTableNum(); ++j) {
//                      String tempSql = executorCtx.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        partition.getTable().setName(tableName + "_" + j);
                        String tempSql = executorCtx.getStatement().toString();
                          
                        Connection conn = executorCtx.getConns().get(i);
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
    
    public int executeUpdate(YYDalExecutorContext executorCtx, YYDalStatement dalStatement)
        throws Exception {
        int total = 0;
        try {
            Partition partition = executorCtx.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = executorCtx.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                total = ps.executeUpdate(dalStatement.getSql());
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
//                String tableName = executorCtx.getTable().getTableName();
//                String tempSql = executorCtx.getSql();
//                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                String tableName = partition.getTable().getName();
                partition.getTable().setName(tableName + "_" + partition.getTableNumber());
                String tempSql = executorCtx.getStatement().toString();
                
                Connection conn = executorCtx.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                total = ps.executeUpdate(tempSql);
            }
            else {
//                String tableName = executorCtx.getTable().getTableName();
                String tableName = partition.getTable().getName();
                
                for (int i = 0; i < executorCtx.getConns().size(); ++i) {
//                    for (int j = 0; j < executorCtx.getTable().getTableNum(); ++j) {
                    for (int j = 0; j < partition.getTableRule().getTableNum(); ++j) {
//                        String tempSql = executorCtx.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        partition.getTable().setName(tableName + "_" + j);
                        String tempSql = executorCtx.getStatement().toString();
                        Connection conn = executorCtx.getConns().get(i);
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
    
    public boolean execute(YYDalExecutorContext executorCtx, YYDalStatement dalStatement)
        throws Exception {
        boolean result = false;
        
        try {
            Partition partition = executorCtx.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = executorCtx.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                result = ps.execute(dalStatement.getSql());
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
//                String tableName = executorCtx.getTable().getTableName();
//                String tempSql = executorCtx.getSql();
//                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                String tableName = partition.getTable().getName();
                partition.getTable().setName(tableName + "_" + partition.getTableNumber());
                String tempSql = executorCtx.getStatement().toString();
                
                Connection conn = executorCtx.getConns().get(0);
                Statement ps = createStatement(conn, dalStatement);
                result = ps.execute(tempSql);
            }
            else {
//                String tableName = executorCtx.getTable().getTableName();
                String tableName = partition.getTable().getName();
                
                for (int i = 0; i < executorCtx.getConns().size(); ++i) {
//                    for (int j = 0; j < executorCtx.getTable().getTableNum(); ++j) {
                    for (int j = 0; j < partition.getTableRule().getTableNum(); ++j) {
                        
//                        String tempSql = executorCtx.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        partition.getTable().setName(tableName + "_" + j);
                        String tempSql = executorCtx.getStatement().toString();
                        
                        Connection conn = executorCtx.getConns().get(i);
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
    

}
