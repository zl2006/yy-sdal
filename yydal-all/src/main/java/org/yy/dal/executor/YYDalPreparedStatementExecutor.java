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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.yy.dal.ds.YYDalPreparedStatement;
import org.yy.dal.route.Partition;

/**
* Sql执行器
* 
* @author  zhouliang
* @version  [1.0, 2016年2月23日]
* @since  [yy-sdal/1.0]
*/
public class YYDalPreparedStatementExecutor extends AbsYYDalExecutor {
    
    public ResultSet executeQuery(YYDalExecutorContext executorCtx, YYDalPreparedStatement dalPreparedStatement)
        throws Exception {
        try {
            Partition partition = executorCtx.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = executorCtx.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(executorCtx.getSql(), conn, dalPreparedStatement);
                setParams(ps, executorCtx.getParams());
                return ps.executeQuery();
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
                String tableName = executorCtx.getTable().getTableName();
                String tempSql = executorCtx.getSql();
                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                Connection conn = executorCtx.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                setParams(ps, executorCtx.getParams());
                return ps.executeQuery();
            }
            else {
                //TODO 处理多个结果集的返回
                String tableName = executorCtx.getTable().getTableName();
                List<ResultSet> results = new ArrayList<ResultSet>(); //使用分为分表的所有实例执行
                for (int i = 0; i < executorCtx.getConns().size(); ++i) {
                    for (int j = 0; j < executorCtx.getTable().getTableNum(); ++j) {
                        String tempSql = executorCtx.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        Connection conn = executorCtx.getConns().get(i);
                        PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                        setParams(ps, executorCtx.getParams());
                        results.add(ps.executeQuery());
                    }
                }
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
    
    public int executeUpdate(YYDalExecutorContext executorCtx, YYDalPreparedStatement dalPreparedStatement)
        throws Exception {
        int total = 0;
        
        try {
            Partition partition = executorCtx.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = executorCtx.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(executorCtx.getSql(), conn, dalPreparedStatement);
                setParams(ps, executorCtx.getParams());
                total = ps.executeUpdate();
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
                String tableName = executorCtx.getTable().getTableName();
                String tempSql = executorCtx.getSql();
                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                Connection conn = executorCtx.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                setParams(ps, executorCtx.getParams());
                total = ps.executeUpdate();
            }
            else {
                String tableName = executorCtx.getTable().getTableName();
                for (int i = 0; i < executorCtx.getConns().size(); ++i) {
                    for (int j = 0; j < executorCtx.getTable().getTableNum(); ++j) {
                        String tempSql = executorCtx.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        Connection conn = executorCtx.getConns().get(i);
                        PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                        setParams(ps, executorCtx.getParams());
                        total += ps.executeUpdate();
                    }
                }
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return total;
    }
    
    public boolean execute(YYDalExecutorContext executorCtx, YYDalPreparedStatement dalPreparedStatement)
        throws Exception {
        boolean result = false;
        
        try {
            Partition partition = executorCtx.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = executorCtx.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(executorCtx.getSql(), conn, dalPreparedStatement);
                setParams(ps, executorCtx.getParams());
                result = ps.execute();
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
                String tableName = executorCtx.getTable().getTableName();
                String tempSql = executorCtx.getSql();
                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                Connection conn = executorCtx.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                setParams(ps, executorCtx.getParams());
                result = ps.execute();
            }
            else {
                String tableName = executorCtx.getTable().getTableName();
                for (int i = 0; i < executorCtx.getConns().size(); ++i) {
                    for (int j = 0; j < executorCtx.getTable().getTableNum(); ++j) {
                        String tempSql = executorCtx.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        Connection conn = executorCtx.getConns().get(i);
                        PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                        setParams(ps, executorCtx.getParams());
                        if (ps.execute()) {
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
