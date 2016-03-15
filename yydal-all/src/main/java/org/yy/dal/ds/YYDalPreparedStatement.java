/*
* 文 件 名:  YYDalPreparedStatement.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  分库分表statement
* 修 改 人:  zhouliang
* 修改时间:  2016年2月18日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds;

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
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.yy.dal.ds.constants.PreparedStatementMode;
import org.yy.dal.ds.support.YYDalStatementSupport;
import org.yy.dal.executor.YYDalExecutor;
import org.yy.dal.executor.YYDalExecutorParam;
import org.yy.dal.nm.DbTable;
import org.yy.dal.parse.JSQLParserException;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.parser.CCJSqlParserUtil;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.route.Partition;
import org.yy.dal.util.ParameterUtil;
import org.yy.dal.util.PartitionUtil;
import org.yy.dal.util.SqlUtil;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
* 分库分表statement
* 
* @author  zhouliang
* @version  [1.0, 2016年2月18日]
* @since  [yy-sdal/1.0]
*/
public class YYDalPreparedStatement extends YYDalStatementSupport implements PreparedStatement {
    
    //原始sql
    private String sql;
    
    //数据源π
    private YYDalDatasource datasource;
    
    //连接
    private YYDalConnection connection;
    
    //参数
    private List<YYDalParameter> paramValues = new ArrayList<YYDalParameter>();
    
    //创建preparedStatement的模式
    private PreparedStatementMode mode;
    
    private int reslutSetType;
    
    private int resultSetConcurrency;
    
    private int resultSetHoldability;
    
    private int autoGeneratedKeys;
    
    private int[] columnIndexes;
    
    private String[] columnNames;
    
    /**
     * 构造statement, 初始化数据源，数据库连接，sql脚本及参数 
     */
    public YYDalPreparedStatement(YYDalDatasource datasource, YYDalConnection connection, String sql) {
        this.datasource = datasource;
        this.connection = connection;
        this.mode = PreparedStatementMode.BASE;
        init(sql);
    }
    
    /**
     * 构造statement, 初始化数据源，数据库连接，sql脚本及参数 
     */
    public YYDalPreparedStatement(YYDalDatasource datasource, YYDalConnection connection, String sql,
        int resultSetType, int resultSetConcurrency) {
        this.datasource = datasource;
        this.connection = connection;
        this.reslutSetType = resultSetType;
        this.resultSetConcurrency = resultSetConcurrency;
        this.mode = PreparedStatementMode.RESLUTSETTYPE_1;
        init(sql);
    }
    
    /**
     * 构造statement, 初始化数据源，数据库连接，sql脚本及参数 
     */
    public YYDalPreparedStatement(YYDalDatasource datasource, YYDalConnection connection, String sql,
        int resultSetType, int resultSetConcurrency, int resultSetHoldability) {
        this.datasource = datasource;
        this.connection = connection;
        this.reslutSetType = resultSetType;
        this.resultSetConcurrency = resultSetConcurrency;
        this.mode = PreparedStatementMode.RESLUTSETTYPE_2;
        this.resultSetHoldability = resultSetHoldability;
        init(sql);
    }
    
    /**
     * 构造statement, 初始化数据源，数据库连接，sql脚本及参数 
     */
    public YYDalPreparedStatement(YYDalDatasource datasource, YYDalConnection connection, String sql,
        int autoGeneratedKeys) {
        this.datasource = datasource;
        this.connection = connection;
        this.autoGeneratedKeys = autoGeneratedKeys;
        this.mode = PreparedStatementMode.AUTOGENERATEKEYS;
        init(sql);
    }
    
    /**
     * 构造statement, 初始化数据源，数据库连接，sql脚本及参数 
     */
    public YYDalPreparedStatement(YYDalDatasource datasource, YYDalConnection connection, String sql,
        int[] columnIndexes) {
        this.datasource = datasource;
        this.connection = connection;
        this.columnIndexes = columnIndexes;
        this.mode = PreparedStatementMode.COLUMNINDEXES;
        init(sql);
    }
    
    /**
     * 构造statement, 初始化数据源，数据库连接，sql脚本及参数 
     */
    public YYDalPreparedStatement(YYDalDatasource datasource, YYDalConnection connection, String sql,
        String[] columnNames) {
        this.datasource = datasource;
        this.connection = connection;
        this.columnNames = columnNames;
        this.mode = PreparedStatementMode.COLUMNNAMES;
        init(sql);
    }
    
    /** {@inheritDoc} */
    @Override
    public YYDalConnection getConnection()
        throws SQLException {
        return this.connection;
    }
    
    /** {@inheritDoc} */
    @Override
    public ResultSet executeQuery(String sql)
        throws SQLException {
        init(sql);
        return executeQuery();
    }
    
    /** {@inheritDoc} */
    @Override
    public int executeUpdate(String sql)
        throws SQLException {
        init(sql);
        return executeUpdate();
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean execute(String sql)
        throws SQLException {
        init(sql);
        return execute();
    }
    
    /** {@inheritDoc} */
    @Override
    public ResultSet executeQuery()
        throws SQLException {
        try {
            YYDalExecutorParam param = initExecutorParam();
            return new YYDalExecutor().executeQuery(param, this);
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public int executeUpdate()
        throws SQLException {
        try {
            YYDalExecutorParam param = initExecutorParam();
            return new YYDalExecutor().executeUpdate(param, this);
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean execute()
        throws SQLException {
        try {
            YYDalExecutorParam param = initExecutorParam();
            return new YYDalExecutor().execute(param, this);
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }
    
    protected YYDalExecutorParam initExecutorParam()
        throws JSQLParserException, SQLException {
        //Step 1, sql参数填充，以便分析sql时能正常取出分表字段的值
        String fillsql = ParameterUtil.fillParam(this.sql, this.paramValues);
        Statement statement = CCJSqlParserUtil.parse(fillsql);
        
        //Step 2, 获取最终选择的实例及分表信息
        Map<String, Table> tables = SqlUtil.getTables(statement); //取语句中用到的表
        Map<String, Expression> whereColumns = SqlUtil.getWhere(statement); //取语句中where的列
        DbTable dbtable = PartitionUtil.partitionTable(tables, datasource.getDbnodeManager()); //取到第一个分库表信息，TODO 有bug,不能是第一个，要取出所有的，并取出参数值范围最小的那个
        Partition partition = PartitionUtil.partition(tables, whereColumns, datasource.getDbnodeManager());
        
        //Step 3, 获取当前sql所要的数据库连接
        List<Connection> conns = fetchConnection(partition, connection);
        
        //Step 4, 执行并返回结果 
        YYDalExecutorParam param =
            new YYDalExecutorParam(conns, this.paramValues, this.sql, CCJSqlParserUtil.parse(this.sql), dbtable,
                partition);
        return param;
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNull(int parameterIndex, int sqlType)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setNull", sqlType));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBoolean(int parameterIndex, boolean x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setBoolean", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setByte(int parameterIndex, byte x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setByte", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setShort(int parameterIndex, short x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setShort", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setInt(int parameterIndex, int x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setInt", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setLong(int parameterIndex, long x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setLong", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setFloat(int parameterIndex, float x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setFloat", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setDouble(int parameterIndex, double x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setDouble", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setBigDecimal", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setString(int parameterIndex, String x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setString", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBytes(int parameterIndex, byte[] x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setBytes", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setDate(int parameterIndex, Date x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setDate", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTime(int parameterIndex, Time x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setTime", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setTimestamp", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setAsciiStream", x, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setUnicodeStream", x, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setBinaryStream", x, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void clearParameters()
        throws SQLException {
        this.paramValues.clear();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setObject", x, targetSqlType));
        throw new SQLFeatureNotSupportedException();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setObject(int parameterIndex, Object x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setObject", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setCharacterStream", reader, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setRef(int parameterIndex, Ref x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setRef", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBlob(int parameterIndex, Blob x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setBlob", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setClob(int parameterIndex, Clob x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setClob", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setArray(int parameterIndex, Array x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setArray", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public ResultSetMetaData getMetaData()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setDate", x, cal));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setTime", x, cal));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setTimestamp", x, cal));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setNull", sqlType, typeName));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setURL(int parameterIndex, URL x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setURL", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public ParameterMetaData getParameterMetaData()
        throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setRowId(int parameterIndex, RowId x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setRowId", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNString(int parameterIndex, String value)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setNString", value));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setNCharacterStream", value, length));
        throw new SQLFeatureNotSupportedException();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNClob(int parameterIndex, NClob value)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setNClob", value));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setClob(int parameterIndex, Reader reader, long length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setClob", reader, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setBlob", inputStream, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNClob(int parameterIndex, Reader reader, long length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setNClob", reader, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setSQLXML", xmlObject));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setObject", x, targetSqlType, scaleOrLength));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setAsciiStream", x, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setBinaryStream", x, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setCharacterStream", reader, length));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setAsciiStream", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setBinaryStream", x));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setCharacterStream", reader));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setNCharacterStream", value));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setClob(int parameterIndex, Reader reader)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setClob", reader));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setBlob", inputStream));
    }
    
    /** {@inheritDoc} */
    @Override
    public void setNClob(int parameterIndex, Reader reader)
        throws SQLException {
        paramValues.set(parameterIndex, new YYDalParameter("setNClob", reader));
    }
    
    public PreparedStatementMode getMode() {
        return mode;
    }
    
    public int getReslutSetType() {
        return reslutSetType;
    }
    
    public int getResultSetConcurrency() {
        return resultSetConcurrency;
    }
    
    public int getResultSetHoldability() {
        return resultSetHoldability;
    }
    
    public int getAutoGeneratedKeys() {
        return autoGeneratedKeys;
    }
    
    public int[] getColumnIndexes() {
        return columnIndexes;
    }
    
    public String[] getColumnNames() {
        return columnNames;
    }
    
    //不用关闭
    /** {@inheritDoc} */
    @Override
    public void close()
        throws SQLException {
        return;
    }
    
    //不用关闭
    /** {@inheritDoc} */
    @Override
    public boolean isClosed()
        throws SQLException {
        return false;
    }
    
    protected void init(String sql) {
        this.sql = sql;
        int paramnum = ParameterUtil.countParams(sql) + 1;
        paramValues = new ArrayList<YYDalParameter>();
        for (int i = 0; i < paramnum; ++i) {
            paramValues.add(i, null);
        }
    }
    
    public static void main(String[] args)
        throws Exception {
        Map<String, String> testMap = new HashMap<String, String>();
        testMap.put("driverClass", "com.mysql.jdbc.Driver");
        testMap.put("jdbcUrl", "jdbc:mysql://localhost:3306/useradmin_inst?useUnicode=true&characterEncoding=UTF8");
        testMap.put("user", "root");
        testMap.put("password", "root");
        testMap.put("minPoolSize", "15");
        testMap.put("acquireIncrement", "8");
        testMap.put("maxPoolSize", "35");
        testMap.put("maxIdleTime", "160");
        
        List<String> tableDescs = new ArrayList<String>();
        tableDescs.add("TB_USER_INFO_[8]:hash(USER_ID)");
        tableDescs.add("TB_PQ_QRCODE_[8]:hash(QRCODE)");
        
        com.mchange.v2.c3p0.ComboPooledDataSource defaultDs = new ComboPooledDataSource();
        BeanUtils.populate(defaultDs, testMap);
        DataSource ds =
            new YYDalDatasource(defaultDs, testMap, "com.mchange.v2.c3p0.ComboPooledDataSource", "c3p0", "mysql",
                "jdbc:mysql://127.0.0.1:3306/useradmin_inst_[0-7]", tableDescs);
        
        Connection connection = ds.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM TB_PQ_QRCODE a WHERE a.QRCODE=?");
        ps.setString(1, "qrcode_val");
        ResultSet rs = ps.executeQuery();
        
        ps.clearParameters();
        rs = ps.executeQuery("select * from TB_PQ_QRCODE");
        
        //ps = connection.prepareStatement("SELECT * FROM TB_WLJ_QRCODE a WHERE a.QRCODE=:qrcode");
        // rs = ps.executeQuery();
        
        //ps = connection.prepareStatement("SELECT * FROM TB_WLJ_QRCODE a WHERE a.QRCODE='abcdef'");
        //rs = ps.executeQuery();
        
        //ps = connection.prepareStatement("SELECT * FROM TB_WLJ_QRCODE a");
        // rs = ps.executeQuery();
        
        //ps = connection.prepareStatement("SELECT * FROM TB_USER_INFO a WHERE a.USER_ID = '123' ");
        //rs = ps.executeQuery();
        
        rs.next();
        
        connection.close();
    }
}
