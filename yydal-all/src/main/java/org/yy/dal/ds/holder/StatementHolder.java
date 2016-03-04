/*
* 文 件 名:  StatementHolder.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  <描述>
* 修 改 人:  zhouliang
* 修改时间:  2016年3月4日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds.holder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
* <一句话功能简述>
* <功能详细描述>
* 
* @author  zhouliang
* @version  [版本号, 2016年3月4日]
* @since  [产品/模块版本]
*/
public class StatementHolder implements Statement {
    
    private Statement statement;
    
    public StatementHolder(Statement statement) {
        this.statement = statement;
    }
    
    /** {@inheritDoc} */
    @Override
    public <T> T unwrap(Class<T> iface)
        throws SQLException {
        return statement.unwrap(iface);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isWrapperFor(Class<?> iface)
        throws SQLException {
        return statement.isWrapperFor(iface);
    }
    
    /** {@inheritDoc} */
    @Override
    public ResultSet executeQuery(String sql)
        throws SQLException {
        return statement.executeQuery(sql);
    }
    
    /** {@inheritDoc} */
    @Override
    public int executeUpdate(String sql)
        throws SQLException {
        return statement.executeUpdate(sql);
    }
    
    /** {@inheritDoc} */
    @Override
    public void close()
        throws SQLException {
        statement.close();
    }
    
    /** {@inheritDoc} */
    @Override
    public int getMaxFieldSize()
        throws SQLException {
        return statement.getMaxFieldSize();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setMaxFieldSize(int max)
        throws SQLException {
        statement.setMaxFieldSize(max);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getMaxRows()
        throws SQLException {
        return statement.getMaxRows();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setMaxRows(int max)
        throws SQLException {
        statement.setMaxRows(max);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setEscapeProcessing(boolean enable)
        throws SQLException {
        statement.setEscapeProcessing(enable);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getQueryTimeout()
        throws SQLException {
        return statement.getQueryTimeout();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setQueryTimeout(int seconds)
        throws SQLException {
        statement.setQueryTimeout(seconds);
    }
    
    /** {@inheritDoc} */
    @Override
    public void cancel()
        throws SQLException {
        statement.cancel();
    }
    
    /** {@inheritDoc} */
    @Override
    public SQLWarning getWarnings()
        throws SQLException {
        return statement.getWarnings();
    }
    
    /** {@inheritDoc} */
    @Override
    public void clearWarnings()
        throws SQLException {
        statement.clearWarnings();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCursorName(String name)
        throws SQLException {
        statement.setCursorName(name);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean execute(String sql)
        throws SQLException {
        return statement.execute(sql);
    }
    
    /** {@inheritDoc} */
    @Override
    public ResultSet getResultSet()
        throws SQLException {
        return statement.getResultSet();
    }
    
    /** {@inheritDoc} */
    @Override
    public int getUpdateCount()
        throws SQLException {
        return statement.getUpdateCount();
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean getMoreResults()
        throws SQLException {
        return statement.getMoreResults();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setFetchDirection(int direction)
        throws SQLException {
        statement.setFetchDirection(direction);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getFetchDirection()
        throws SQLException {
        return statement.getFetchDirection();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setFetchSize(int rows)
        throws SQLException {
        statement.setFetchSize(rows);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getFetchSize()
        throws SQLException {
        return statement.getFetchSize();
    }
    
    /** {@inheritDoc} */
    @Override
    public int getResultSetConcurrency()
        throws SQLException {
        return statement.getResultSetConcurrency();
    }
    
    /** {@inheritDoc} */
    @Override
    public int getResultSetType()
        throws SQLException {
        return statement.getResultSetType();
    }
    
    /** {@inheritDoc} */
    @Override
    public void addBatch(String sql)
        throws SQLException {
        statement.addBatch(sql);
    }
    
    /** {@inheritDoc} */
    @Override
    public void clearBatch()
        throws SQLException {
        statement.clearBatch();
    }
    
    /** {@inheritDoc} */
    @Override
    public int[] executeBatch()
        throws SQLException {
        return statement.executeBatch();
    }
    
    /** {@inheritDoc} */
    @Override
    public Connection getConnection()
        throws SQLException {
        return statement.getConnection();
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean getMoreResults(int current)
        throws SQLException {
        return statement.getMoreResults();
    }
    
    /** {@inheritDoc} */
    @Override
    public ResultSet getGeneratedKeys()
        throws SQLException {
        return statement.getGeneratedKeys();
    }
    
    /** {@inheritDoc} */
    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys)
        throws SQLException {
        return statement.executeUpdate(sql, autoGeneratedKeys);
    }
    
    /** {@inheritDoc} */
    @Override
    public int executeUpdate(String sql, int[] columnIndexes)
        throws SQLException {
        return statement.executeUpdate(sql, columnIndexes);
    }
    
    /** {@inheritDoc} */
    @Override
    public int executeUpdate(String sql, String[] columnNames)
        throws SQLException {
        return statement.executeUpdate(sql, columnNames);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean execute(String sql, int autoGeneratedKeys)
        throws SQLException {
        return statement.execute(sql, autoGeneratedKeys);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean execute(String sql, int[] columnIndexes)
        throws SQLException {
        return statement.execute(sql, columnIndexes);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean execute(String sql, String[] columnNames)
        throws SQLException {
        return statement.execute(sql, columnNames);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getResultSetHoldability()
        throws SQLException {
        return statement.getResultSetHoldability();
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isClosed()
        throws SQLException {
        return statement.isClosed();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setPoolable(boolean poolable)
        throws SQLException {
        statement.setPoolable(poolable);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isPoolable()
        throws SQLException {
        return statement.isPoolable();
    }
    
    /** {@inheritDoc} */
    @Override
    public void closeOnCompletion()
        throws SQLException {
        statement.closeOnCompletion();
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isCloseOnCompletion()
        throws SQLException {
        return statement.isCloseOnCompletion();
    }
    
}
