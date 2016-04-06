/*
* 文 件 名:  ConnectionHolder.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  数据库连接代理类
* 修 改 人:  zhouliang
* 修改时间:  2016年3月2日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds.holder;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.yy.dal.ds.ConnectionStatus;

/**
* 数据库连接代理类
* 
* @author  zhouliang
* @version  [1.0, 2016年3月2日]
* @since  [yy-sdal/1.0]
*/
public class ConnectionHolder implements Connection {
    
    /**
     * 实际连接
     */
    private Connection connection;
    
    /**
     * 连接状态
     */
    private ConnectionStatus status;
    
    //实际获取连接时重置在YYDalConnection中设置的参数
    public ConnectionHolder(Connection connection, ConnectionStatus status) {
        this.connection = connection;
        this.status = status;
        try {
            if (this.status.isAutoCommit() != null) {
                connection.setAutoCommit(status.isAutoCommit());
            }
            if (this.status.isReadOnly() != null) {
                connection.setReadOnly(status.isReadOnly());
            }
            if (this.status.getCatalog() != null) {
                connection.setCatalog(status.getCatalog());
            }
            if (this.status.getTransactionIsolation() != null) {
                connection.setTransactionIsolation(status.getTransactionIsolation());
            }
            if (this.status.getHoldability() != null) {
                connection.setHoldability(status.getHoldability());
            }
            if (this.status.getSchema() != null) {
                connection.setSchema(status.getSchema());
            }
            if(this.status.getClientProperties().size() > 0){
                connection.setClientInfo(status.getClientProperties());
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public <T> T unwrap(Class<T> iface)
        throws SQLException {
        return connection.unwrap(iface);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isWrapperFor(Class<?> iface)
        throws SQLException {
        return connection.isWrapperFor(iface);
    }
    
    /** {@inheritDoc} */
    @Override
    public Statement createStatement()
        throws SQLException {
        return connection.createStatement();
    }
    
    /** {@inheritDoc} */
    @Override
    public PreparedStatement prepareStatement(String sql)
        throws SQLException {
        return connection.prepareStatement(sql);
    }
    
    /** {@inheritDoc} */
    @Override
    public CallableStatement prepareCall(String sql)
        throws SQLException {
        return connection.prepareCall(sql);
    }
    
    /** {@inheritDoc} */
    @Override
    public String nativeSQL(String sql)
        throws SQLException {
        return connection.nativeSQL(sql);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setAutoCommit(boolean autoCommit)
        throws SQLException {
        connection.setAutoCommit(autoCommit);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean getAutoCommit()
        throws SQLException {
        return connection.getAutoCommit();
    }
    
    /** {@inheritDoc} */
    @Override
    public void commit()
        throws SQLException {
        connection.commit();
    }
    
    /** {@inheritDoc} */
    @Override
    public void rollback()
        throws SQLException {
        connection.rollback();
    }
    
    /** {@inheritDoc} */
    @Override
    public void close()
        throws SQLException {
        connection.close();
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isClosed()
        throws SQLException {
        return connection.isClosed();
    }
    
    /** {@inheritDoc} */
    @Override
    public DatabaseMetaData getMetaData()
        throws SQLException {
        return connection.getMetaData();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setReadOnly(boolean readOnly)
        throws SQLException {
        connection.setReadOnly(readOnly);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isReadOnly()
        throws SQLException {
        return connection.isReadOnly();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCatalog(String catalog)
        throws SQLException {
        connection.setCatalog(catalog);
    }
    
    /** {@inheritDoc} */
    @Override
    public String getCatalog()
        throws SQLException {
        return connection.getCatalog();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTransactionIsolation(int level)
        throws SQLException {
        connection.setTransactionIsolation(level);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getTransactionIsolation()
        throws SQLException {
        return connection.getTransactionIsolation();
    }
    
    /** {@inheritDoc} */
    @Override
    public SQLWarning getWarnings()
        throws SQLException {
        return connection.getWarnings();
    }
    
    /** {@inheritDoc} */
    @Override
    public void clearWarnings()
        throws SQLException {
        connection.clearWarnings();
    }
    
    /** {@inheritDoc} */
    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency)
        throws SQLException {
        return connection.createStatement(resultSetType, resultSetConcurrency);
    }
    
    /** {@inheritDoc} */
    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
        throws SQLException {
        return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    
    /** {@inheritDoc} */
    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
        throws SQLException {
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
    }
    
    /** {@inheritDoc} */
    @Override
    public Map<String, Class<?>> getTypeMap()
        throws SQLException {
        return connection.getTypeMap();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTypeMap(Map<String, Class<?>> map)
        throws SQLException {
        connection.setTypeMap(map);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setHoldability(int holdability)
        throws SQLException {
        connection.setHoldability(holdability);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getHoldability()
        throws SQLException {
        return connection.getHoldability();
    }
    
    /** {@inheritDoc} */
    @Override
    public Savepoint setSavepoint()
        throws SQLException {
        return connection.setSavepoint();
    }
    
    /** {@inheritDoc} */
    @Override
    public Savepoint setSavepoint(String name)
        throws SQLException {
        return connection.setSavepoint(name);
    }
    
    /** {@inheritDoc} */
    @Override
    public void rollback(Savepoint savepoint)
        throws SQLException {
        connection.rollback(savepoint);
    }
    
    /** {@inheritDoc} */
    @Override
    public void releaseSavepoint(Savepoint savepoint)
        throws SQLException {
        connection.releaseSavepoint(savepoint);
    }
    
    /** {@inheritDoc} */
    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
        throws SQLException {
        return connection.createStatement(resultSetType, resultSetConcurrency);
    }
    
    /** {@inheritDoc} */
    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
        int resultSetHoldability)
        throws SQLException {
        return connection.prepareStatement(sql);
    }
    
    /** {@inheritDoc} */
    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
        int resultSetHoldability)
        throws SQLException {
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
    }
    
    /** {@inheritDoc} */
    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
        throws SQLException {
        return connection.prepareStatement(sql, autoGeneratedKeys);
    }
    
    /** {@inheritDoc} */
    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
        throws SQLException {
        return connection.prepareStatement(sql, columnIndexes);
    }
    
    /** {@inheritDoc} */
    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames)
        throws SQLException {
        return connection.prepareStatement(sql, columnNames);
    }
    
    /** {@inheritDoc} */
    @Override
    public Clob createClob()
        throws SQLException {
        return connection.createClob();
    }
    
    /** {@inheritDoc} */
    @Override
    public Blob createBlob()
        throws SQLException {
        return connection.createBlob();
    }
    
    /** {@inheritDoc} */
    @Override
    public NClob createNClob()
        throws SQLException {
        return connection.createNClob();
    }
    
    /** {@inheritDoc} */
    @Override
    public SQLXML createSQLXML()
        throws SQLException {
        return connection.createSQLXML();
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isValid(int timeout)
        throws SQLException {
        return connection.isValid(timeout);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setClientInfo(String name, String value)
        throws SQLClientInfoException {
        connection.setClientInfo(name, value);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setClientInfo(Properties properties)
        throws SQLClientInfoException {
        connection.setClientInfo(properties);
    }
    
    /** {@inheritDoc} */
    @Override
    public String getClientInfo(String name)
        throws SQLException {
        return connection.getClientInfo(name);
    }
    
    /** {@inheritDoc} */
    @Override
    public Properties getClientInfo()
        throws SQLException {
        return connection.getClientInfo();
    }
    
    /** {@inheritDoc} */
    @Override
    public Array createArrayOf(String typeName, Object[] elements)
        throws SQLException {
        return connection.createArrayOf(typeName, elements);
    }
    
    /** {@inheritDoc} */
    @Override
    public Struct createStruct(String typeName, Object[] attributes)
        throws SQLException {
        return connection.createStruct(typeName, attributes);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setSchema(String schema)
        throws SQLException {
        connection.setSchema(schema);
    }
    
    /** {@inheritDoc} */
    @Override
    public String getSchema()
        throws SQLException {
        return connection.getSchema();
    }
    
    /** {@inheritDoc} */
    @Override
    public void abort(Executor executor)
        throws SQLException {
        connection.abort(executor);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds)
        throws SQLException {
        connection.setNetworkTimeout(executor, milliseconds);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getNetworkTimeout()
        throws SQLException {
        return connection.getNetworkTimeout();
    }
    
}
