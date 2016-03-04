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
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;

import org.yy.dal.ds.YYDalParameter;
import org.yy.dal.ds.YYDalPreparedStatement;
import org.yy.dal.ds.constants.PreparedStatementMode;
import org.yy.dal.route.Partition;

/**
* Sql执行器
* 
* @author  zhouliang
* @version  [1.0, 2016年2月23日]
* @since  [yy-sdal/1.0]
*/
public class YYDalExecutor {
    
    public ResultSet executeQuery(YYDalExecutorParam param, YYDalPreparedStatement dalPreparedStatement)
        throws Exception {
        String tableName = param.getTable().getTableName();
        try {
            Partition partition = param.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = param.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(param.getSql(), conn, dalPreparedStatement);
                setParams(ps, param.getParams());
                return ps.executeQuery();
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
                String tempSql = param.getSql();
                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                Connection conn = param.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                setParams(ps, param.getParams());
                return ps.executeQuery();
            }
            else {
                //TODO 处理多个结果集的返回
                List<ResultSet> results = new ArrayList<ResultSet>(); //使用分为分表的所有实例执行
                for (int i = 0; i < param.getConns().size(); ++i) {
                    for (int j = 0; j < param.getTable().getTableNum(); ++j) {
                        String tempSql = param.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        Connection conn = param.getConns().get(i);
                        PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                        setParams(ps, param.getParams());
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
    
    public int executeUpdate(YYDalExecutorParam param, YYDalPreparedStatement dalPreparedStatement)
        throws Exception {
        int total = 0;
        String tableName = param.getTable().getTableName();
        try {
            Partition partition = param.getPartition();
            if (partition == null) { //不使用分库分表情况下执行
                Connection conn = param.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(param.getSql(), conn, dalPreparedStatement);
                setParams(ps, param.getParams());
                total = ps.executeUpdate();
            }
            else if (partition.getInstNumber() > -1) { //使用分库分表的某个实例执行
                String tempSql = param.getSql();
                tempSql = tempSql.replaceAll("(?i:" + tableName + ")", tableName + "_" + partition.getTableNumber());
                Connection conn = param.getConns().get(0);
                PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                setParams(ps, param.getParams());
                total = ps.executeUpdate();
            }
            else {
                for (int i = 0; i < param.getConns().size(); ++i) {
                    for (int j = 0; j < param.getTable().getTableNum(); ++j) {
                        String tempSql = param.getSql().replaceAll("(?i:" + tableName + ")", tableName + "_" + j);
                        Connection conn = param.getConns().get(i);
                        PreparedStatement ps = createPreparedStatement(tempSql, conn, dalPreparedStatement);
                        setParams(ps, param.getParams());
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
    
    public boolean execute(YYDalExecutorParam param) {
        return false;
    }
    
    /**
     * 设置SQL参数
     */
    private void setParams(PreparedStatement statement, List<YYDalParameter> params)
        throws Exception {
        if (params == null || params.size() == 0) {
            return;
        }
        
        for (int i = 1; i < params.size(); ++i) { //设置参数方法为1个参数时
            YYDalParameter item = params.get(i);
            if (item.getNum() == 1) {
                statement.getClass()
                    .getMethod(item.getProperty(), int.class, item.getParam1().getClass())
                    .invoke(statement, i, item.getParam1());
            }
            else if (item.getNum() == 2) {//设置参数方法为2个参数时
                statement.getClass()
                    .getMethod(item.getProperty(), int.class, item.getParam1().getClass(), item.getParam2().getClass())
                    .invoke(statement, i, item.getParam1(), item.getParam2());
            }
            else if (item.getNum() == 3) {//设置参数方法为3个参数时
                statement.getClass()
                    .getMethod(item.getProperty(),
                        int.class,
                        item.getParam1().getClass(),
                        item.getParam2().getClass(),
                        item.getParam3().getClass())
                    .invoke(statement, i, item.getParam1(), item.getParam2(), item.getParam3());
            }
            else if (item.getNum() == 4) {//设置参数方法为4个参数时
                statement.getClass()
                    .getMethod(item.getProperty(),
                        int.class,
                        item.getParam1().getClass(),
                        item.getParam2().getClass(),
                        item.getParam3().getClass(),
                        item.getParam4().getClass())
                    .invoke(statement, i, item.getParam1(), item.getParam2(), item.getParam3(), item.getParam4());
            }
        }
    }
    
    /**
     * 根据不同模式创建PreparedStatement
     */
    private PreparedStatement createPreparedStatement(String sql, Connection conn,
        YYDalPreparedStatement dalPreparedStatement)
        throws SQLException {
        if (dalPreparedStatement.getMode() == PreparedStatementMode.BASE) {
            return conn.prepareStatement(sql);
        }
        else if (dalPreparedStatement.getMode() == PreparedStatementMode.RESLUTSETTYPE_1) {
            return conn.prepareStatement(sql,
                dalPreparedStatement.getReslutSetType(),
                dalPreparedStatement.getResultSetConcurrency());
        }
        else if (dalPreparedStatement.getMode() == PreparedStatementMode.RESLUTSETTYPE_2) {
            return conn.prepareStatement(sql,
                dalPreparedStatement.getReslutSetType(),
                dalPreparedStatement.getResultSetConcurrency(),
                dalPreparedStatement.getResultSetHoldability());
        }
        else if (dalPreparedStatement.getMode() == PreparedStatementMode.AUTOGENERATEKEYS) {
            return conn.prepareStatement(sql, dalPreparedStatement.getAutoGeneratedKeys());
        }
        else if (dalPreparedStatement.getMode() == PreparedStatementMode.COLUMNINDEXES) {
            return conn.prepareStatement(sql, dalPreparedStatement.getColumnIndexes());
        }
        else if (dalPreparedStatement.getMode() == PreparedStatementMode.COLUMNNAMES) {
            return conn.prepareStatement(sql, dalPreparedStatement.getColumnNames());
        }
        throw new SQLFeatureNotSupportedException();
    }
    
}
