/*
* 文 件 名:  PreparedStatementHolder.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  <描述>
* 修 改 人:  zhouliang
* 修改时间:  2016年3月4日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds.holder;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
* <一句话功能简述>
* <功能详细描述>
* 
* @author  zhouliang
* @version  [版本号, 2016年3月4日]
* @since  [产品/模块版本]
*/
public class PreparedStatementHolder implements PreparedStatement {
    
    private PreparedStatement statement;
    
    public PreparedStatementHolder(PreparedStatement statement) {
        this.statement = statement;
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
        statement.addBatch();
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
        return statement.getMoreResults(current);
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
    public ResultSet executeQuery()
        throws SQLException {
        return statement.executeQuery();
    }
    
    /** {@inheritDoc} */
    @Override
    public int executeUpdate()
        throws SQLException {
        return statement.executeUpdate();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNull(int parameterIndex, int sqlType)
        throws SQLException {
        statement.setNull(parameterIndex, sqlType);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBoolean(int parameterIndex, boolean x)
        throws SQLException {
        statement.setBoolean(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setByte(int parameterIndex, byte x)
        throws SQLException {
        statement.setByte(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setShort(int parameterIndex, short x)
        throws SQLException {
        statement.setShort(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setInt(int parameterIndex, int x)
        throws SQLException {
        statement.setInt(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setLong(int parameterIndex, long x)
        throws SQLException {
        statement.setLong(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setFloat(int parameterIndex, float x)
        throws SQLException {
        statement.setFloat(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setDouble(int parameterIndex, double x)
        throws SQLException {
        statement.setDouble(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x)
        throws SQLException {
        statement.setBigDecimal(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setString(int parameterIndex, String x)
        throws SQLException {
        statement.setString(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBytes(int parameterIndex, byte[] x)
        throws SQLException {
        statement.setBytes(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setDate(int parameterIndex, Date x)
        throws SQLException {
        statement.setDate(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTime(int parameterIndex, Time x)
        throws SQLException {
        statement.setTime(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x)
        throws SQLException {
        statement.setTimestamp(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length)
        throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("deprecation")
    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length)
        throws SQLException {
        statement.setUnicodeStream(parameterIndex, x, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length)
        throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void clearParameters()
        throws SQLException {
        statement.clearParameters();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType)
        throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setObject(int parameterIndex, Object x)
        throws SQLException {
        statement.setObject(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean execute()
        throws SQLException {
        return statement.execute();
    }
    
    /** {@inheritDoc} */
    @Override
    public void addBatch()
        throws SQLException {
        statement.addBatch();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length)
        throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setRef(int parameterIndex, Ref x)
        throws SQLException {
        statement.setRef(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBlob(int parameterIndex, Blob x)
        throws SQLException {
        statement.setBlob(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setClob(int parameterIndex, Clob x)
        throws SQLException {
        statement.setClob(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setArray(int parameterIndex, Array x)
        throws SQLException {
        statement.setArray(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public ResultSetMetaData getMetaData()
        throws SQLException {
        return statement.getMetaData();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal)
        throws SQLException {
        statement.setDate(parameterIndex, x, cal);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal)
        throws SQLException {
        statement.setTime(parameterIndex, x, cal);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
        throws SQLException {
        statement.setTimestamp(parameterIndex, x, cal);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName)
        throws SQLException {
        statement.setNull(parameterIndex, sqlType, typeName);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setURL(int parameterIndex, URL x)
        throws SQLException {
        statement.setURL(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public ParameterMetaData getParameterMetaData()
        throws SQLException {
        return statement.getParameterMetaData();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setRowId(int parameterIndex, RowId x)
        throws SQLException {
        statement.setRowId(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNString(int parameterIndex, String value)
        throws SQLException {
        statement.setNString(parameterIndex, value);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length)
        throws SQLException {
        statement.setNCharacterStream(parameterIndex, value, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNClob(int parameterIndex, NClob value)
        throws SQLException {
        statement.setNClob(parameterIndex, value);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setClob(int parameterIndex, Reader reader, long length)
        throws SQLException {
        statement.setClob(parameterIndex, reader, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
        throws SQLException {
        statement.setBlob(parameterIndex, inputStream, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNClob(int parameterIndex, Reader reader, long length)
        throws SQLException {
        statement.setNClob(parameterIndex, reader, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
        throws SQLException {
        statement.setSQLXML(parameterIndex, xmlObject);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
        throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length)
        throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length)
        throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length)
        throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x)
        throws SQLException {
        statement.setAsciiStream(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x)
        throws SQLException {
        statement.setBinaryStream(parameterIndex, x);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader)
        throws SQLException {
        statement.setCharacterStream(parameterIndex, reader);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value)
        throws SQLException {
        statement.setNCharacterStream(parameterIndex, value);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setClob(int parameterIndex, Reader reader)
        throws SQLException {
        statement.setClob(parameterIndex, reader);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream)
        throws SQLException {
        statement.setBlob(parameterIndex, inputStream);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNClob(int parameterIndex, Reader reader)
        throws SQLException {
        statement.setNClob(parameterIndex, reader);
    }
    
}
