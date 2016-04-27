/*
* 文 件 名:  IteratorResultSet.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  处理ResultSet集合
* 修 改 人:  zhouliang
* 修改时间:  2016年4月8日
* 修改内容:  <修改内容>
*/
package org.yy.dal.merge;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.yy.dal.ds.support.YYDalResultSetNotSupport;

/**
* 处理ResultSet集合
* 
* @author  zhouliang
* @version  [1.0, 2016年4月8日]
* @since  [yy-sdal/1.0]
*/
public abstract class AbsYYDalResultSet extends YYDalResultSetNotSupport {
    
    //当前读取的ResultSet
    private ResultSet currentRs;
    
    //待读取的ResultSet
    private List<ResultSet> resultSets;
    
    //已读完的ResultSet
    private List<ResultSet> finishResultSets;
    
    //关闭状态
    private boolean close;
    
    private boolean offsetSkipped;
    
    private int readCount;
    
    /** {@inheritDoc} */
    @Override
    public boolean next()
        throws SQLException {
        return false;
    }
    
    //获取下一个Result
    protected abstract boolean nextSharing();
    
    /** {@inheritDoc} */
    @Override
    public void close()
        throws SQLException {
        close = true;
        for (ResultSet rs : resultSets) {
            rs.close();
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean wasNull()
        throws SQLException {
        return currentRs.wasNull();
    }
    
    /** {@inheritDoc} */
    @Override
    public String getString(int columnIndex)
        throws SQLException {
        return currentRs.getString(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean getBoolean(int columnIndex)
        throws SQLException {
        return currentRs.getBoolean(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public byte getByte(int columnIndex)
        throws SQLException {
        return currentRs.getByte(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public short getShort(int columnIndex)
        throws SQLException {
        return currentRs.getShort(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getInt(int columnIndex)
        throws SQLException {
        return currentRs.getInt(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public long getLong(int columnIndex)
        throws SQLException {
        return currentRs.getLong(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public float getFloat(int columnIndex)
        throws SQLException {
        return currentRs.getFloat(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public double getDouble(int columnIndex)
        throws SQLException {
        return currentRs.getDouble(columnIndex);
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("deprecation")
    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale)
        throws SQLException {
        return currentRs.getBigDecimal(columnIndex, scale);
    }
    
    /** {@inheritDoc} */
    @Override
    public byte[] getBytes(int columnIndex)
        throws SQLException {
        return currentRs.getBytes(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public Date getDate(int columnIndex)
        throws SQLException {
        return currentRs.getDate(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public Time getTime(int columnIndex)
        throws SQLException {
        return currentRs.getTime(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public Timestamp getTimestamp(int columnIndex)
        throws SQLException {
        return currentRs.getTimestamp(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public InputStream getAsciiStream(int columnIndex)
        throws SQLException {
        return currentRs.getAsciiStream(columnIndex);
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("deprecation")
    @Override
    public InputStream getUnicodeStream(int columnIndex)
        throws SQLException {
        return currentRs.getUnicodeStream(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public InputStream getBinaryStream(int columnIndex)
        throws SQLException {
        return currentRs.getBinaryStream(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public String getString(String columnLabel)
        throws SQLException {
        return currentRs.getString(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean getBoolean(String columnLabel)
        throws SQLException {
        return currentRs.getBoolean(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public byte getByte(String columnLabel)
        throws SQLException {
        return currentRs.getByte(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public short getShort(String columnLabel)
        throws SQLException {
        return currentRs.getShort(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getInt(String columnLabel)
        throws SQLException {
        return currentRs.getInt(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public long getLong(String columnLabel)
        throws SQLException {
        return currentRs.getLong(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public float getFloat(String columnLabel)
        throws SQLException {
        return currentRs.getFloat(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public double getDouble(String columnLabel)
        throws SQLException {
        return currentRs.getDouble(columnLabel);
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("deprecation")
    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale)
        throws SQLException {
        return currentRs.getBigDecimal(columnLabel, scale);
    }
    
    /** {@inheritDoc} */
    @Override
    public byte[] getBytes(String columnLabel)
        throws SQLException {
        return currentRs.getBytes(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public Date getDate(String columnLabel)
        throws SQLException {
        return currentRs.getDate(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public Time getTime(String columnLabel)
        throws SQLException {
        return currentRs.getTime(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public Timestamp getTimestamp(String columnLabel)
        throws SQLException {
        return currentRs.getTimestamp(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public InputStream getAsciiStream(String columnLabel)
        throws SQLException {
        return currentRs.getAsciiStream(columnLabel);
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("deprecation")
    @Override
    public InputStream getUnicodeStream(String columnLabel)
        throws SQLException {
        return currentRs.getUnicodeStream(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public InputStream getBinaryStream(String columnLabel)
        throws SQLException {
        return currentRs.getBinaryStream(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public SQLWarning getWarnings()
        throws SQLException {
        return currentRs.getWarnings();
    }
    
    /** {@inheritDoc} */
    @Override
    public void clearWarnings()
        throws SQLException {
        currentRs.clearWarnings();
    }
    
    /** {@inheritDoc} */
    @Override
    public ResultSetMetaData getMetaData()
        throws SQLException {
        return currentRs.getMetaData();
    }
    
    /** {@inheritDoc} */
    @Override
    public Object getObject(int columnIndex)
        throws SQLException {
        return currentRs.getObject(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public Object getObject(String columnLabel)
        throws SQLException {
        return currentRs.getObject(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public int findColumn(String columnLabel)
        throws SQLException {
        return currentRs.findColumn(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public Reader getCharacterStream(int columnIndex)
        throws SQLException {
        return currentRs.getCharacterStream(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public Reader getCharacterStream(String columnLabel)
        throws SQLException {
        return currentRs.getCharacterStream(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public BigDecimal getBigDecimal(int columnIndex)
        throws SQLException {
        return currentRs.getBigDecimal(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public BigDecimal getBigDecimal(String columnLabel)
        throws SQLException {
        return currentRs.getBigDecimal(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setFetchDirection(int direction)
        throws SQLException {
        for (ResultSet each : resultSets) {
            each.setFetchDirection(direction);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public int getFetchDirection()
        throws SQLException {
        return currentRs.getFetchDirection();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setFetchSize(int rows)
        throws SQLException {
        for (ResultSet each : resultSets) {
            each.setFetchSize(rows);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public int getFetchSize()
        throws SQLException {
        return currentRs.getFetchSize();
    }
    
    /** {@inheritDoc} */
    @Override
    public int getType()
        throws SQLException {
        return currentRs.getType();
    }
    
    /** {@inheritDoc} */
    @Override
    public int getConcurrency()
        throws SQLException {
        return currentRs.getConcurrency();
    }
    
    /** {@inheritDoc} */
    @Override
    public Statement getStatement()
        throws SQLException {
        return currentRs.getStatement();
    }
    
    /** {@inheritDoc} */
    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map)
        throws SQLException {
        return currentRs.getObject(columnIndex, map);
    }
    
    /** {@inheritDoc} */
    @Override
    public Blob getBlob(int columnIndex)
        throws SQLException {
        return currentRs.getBlob(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public Clob getClob(int columnIndex)
        throws SQLException {
        return currentRs.getClob(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map)
        throws SQLException {
        return currentRs.getObject(columnLabel, map);
    }
    
    /** {@inheritDoc} */
    @Override
    public Blob getBlob(String columnLabel)
        throws SQLException {
        return currentRs.getBlob(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public Clob getClob(String columnLabel)
        throws SQLException {
        return currentRs.getClob(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public Date getDate(int columnIndex, Calendar cal)
        throws SQLException {
        return currentRs.getDate(columnIndex, cal);
    }
    
    /** {@inheritDoc} */
    @Override
    public Date getDate(String columnLabel, Calendar cal)
        throws SQLException {
        return currentRs.getDate(columnLabel, cal);
    }
    
    /** {@inheritDoc} */
    @Override
    public Time getTime(int columnIndex, Calendar cal)
        throws SQLException {
        return currentRs.getTime(columnIndex, cal);
    }
    
    /** {@inheritDoc} */
    @Override
    public Time getTime(String columnLabel, Calendar cal)
        throws SQLException {
        return currentRs.getTime(columnLabel, cal);
    }
    
    /** {@inheritDoc} */
    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal)
        throws SQLException {
        return currentRs.getTimestamp(columnIndex, cal);
    }
    
    /** {@inheritDoc} */
    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal)
        throws SQLException {
        return currentRs.getTimestamp(columnLabel, cal);
    }
    
    /** {@inheritDoc} */
    @Override
    public URL getURL(int columnIndex)
        throws SQLException {
        return currentRs.getURL(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public URL getURL(String columnLabel)
        throws SQLException {
        return currentRs.getURL(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isClosed()
        throws SQLException {
        return this.close;
    }
    
    /** {@inheritDoc} */
    @Override
    public SQLXML getSQLXML(int columnIndex)
        throws SQLException {
        return currentRs.getSQLXML(columnIndex);
    }
    
    /** {@inheritDoc} */
    @Override
    public SQLXML getSQLXML(String columnLabel)
        throws SQLException {
        return currentRs.getSQLXML(columnLabel);
    }
    
    /** {@inheritDoc} */
    @Override
    public <T> T unwrap(Class<T> iface)
        throws SQLException {
        return currentRs.unwrap(iface);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isWrapperFor(Class<?> iface)
        throws SQLException {
        return currentRs.isWrapperFor(iface);
    }
    
}
