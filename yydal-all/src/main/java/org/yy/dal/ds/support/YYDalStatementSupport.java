/*
* 文 件 名:  YYDalStatementSupport.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  sql语句的一些支持操作
* 修 改 人:  zhouliang
* 修改时间:  2016年2月23日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds.support;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;

import org.yy.dal.ds.YYDalConnection;
import org.yy.dal.ds.YYDalDatasource;
import org.yy.dal.ds.holder.ConnectionHolder;
import org.yy.dal.route.Partition;

/**
* sql语句的一些支持操作
* 
* @author  zhouliang
* @version  [1.0, 2016年2月23日]
* @since  [yy-sdal/1.0]
*/
public class YYDalStatementSupport {
    
    //默认连接key
    private static String DEFAULT_CONNECT = "DEFAULT";
    
    /**
     * 根据分区获取连接信息 
     * 处理三种情况，返回null时使用默认实例，返回[-1,-1]时使用分库所有实例，返回其它值时使用其中一个实例
     * 
     * @param partitions 
     * @param datasource
     * @return
     */
    public List<Connection> fetchConnection(Partition partition, YYDalConnection connection)
        throws SQLException {
        
        List<Connection> conns = new ArrayList<Connection>();
        YYDalDatasource ds = connection.getDatasource();
        
        //处理三种情况，返回null时使用默认实例，，
        ConnectionHolder temp = null;
        if (partition == null) {
            temp = connection.get(DEFAULT_CONNECT);
            if (temp == null) {
                temp = new ConnectionHolder(ds.getDefaultDataSource().getConnection(), connection.getStatus());
                connection.put(DEFAULT_CONNECT, temp);
                conns.add(temp);
            }
        }
        else if (partition.getInstNumber() == -1) {//返回[-1,-1]时使用分库所有实例
            for (int i = 0; i < ds.getDatasource().length; ++i) {
                temp = connection.get(i + "");
                if (temp == null) {
                    temp = new ConnectionHolder(ds.getDatasource()[i].getConnection(), connection.getStatus());
                    connection.put(i + "", temp);
                    conns.add(temp);
                }
            }
        }
        else {//返回其它值时使用其中一个实例
            temp = connection.get(partition.getInstNumber() + "");
            if (temp == null) {
                temp =
                    new ConnectionHolder(ds.getDatasource()[partition.getInstNumber()].getConnection(),
                        connection.getStatus());
                connection.put(partition.getInstNumber() + "", temp);
                conns.add(temp);
            }
        }
        return conns;
    }
    
    
    public int getMaxFieldSize()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setMaxFieldSize(int max)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int getMaxRows()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setMaxRows(int max)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setEscapeProcessing(boolean enable)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int getQueryTimeout()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setQueryTimeout(int seconds)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void cancel()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public SQLWarning getWarnings()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void clearWarnings()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setCursorName(String name)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public ResultSet getResultSet()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int getUpdateCount()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public boolean getMoreResults()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setFetchDirection(int direction)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int getFetchDirection()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setFetchSize(int rows)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
        
    }
    
    public int getFetchSize()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int getResultSetConcurrency()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int getResultSetType()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void addBatch(String sql)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void clearBatch()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int[] executeBatch()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public boolean getMoreResults(int current)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public ResultSet getGeneratedKeys()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int executeUpdate(String sql, int autoGeneratedKeys)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int executeUpdate(String sql, int[] columnIndexes)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int executeUpdate(String sql, String[] columnNames)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public boolean execute(String sql, int autoGeneratedKeys)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public boolean execute(String sql, int[] columnIndexes)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public boolean execute(String sql, String[] columnNames)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setPoolable(boolean poolable)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public boolean isPoolable()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void closeOnCompletion()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public boolean isCloseOnCompletion()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public <T> T unwrap(Class<T> iface)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public boolean isWrapperFor(Class<?> iface)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void addBatch()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int getResultSetHoldability()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
