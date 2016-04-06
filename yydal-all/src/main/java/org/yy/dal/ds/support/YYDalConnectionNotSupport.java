/*
* 文 件 名:  YYDalConnectionNotSupport.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  数据库连接不支持的方法
* 修 改 人:  zhouliang
* 修改时间:  2016年3月2日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds.support;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;
import java.util.Map;
import java.util.concurrent.Executor;

/**
* 数据库连接不支持的方法
* 
* @author  zhouliang
* @version  [1.0, 2016年3月2日]
* @since  [yy-dal/1.0]
*/
public class YYDalConnectionNotSupport {
    
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
        int resultSetHoldability)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public CallableStatement prepareCall(String sql)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public Savepoint setSavepoint()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public Savepoint setSavepoint(String name)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void rollback(Savepoint savepoint)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void releaseSavepoint(Savepoint savepoint)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public Array createArrayOf(String typeName, Object[] elements)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public Struct createStruct(String typeName, Object[] attributes)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public Clob createClob()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public Blob createBlob()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public NClob createNClob()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public SQLXML createSQLXML()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public boolean isValid(int timeout)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public DatabaseMetaData getMetaData()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public Map<String, Class<?>> getTypeMap()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setTypeMap(Map<String, Class<?>> map)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public void setNetworkTimeout(Executor executor, int milliseconds)
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    public int getNetworkTimeout()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
