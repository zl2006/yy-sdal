/*
* 文 件 名:  YYDalStatement.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  <描述>
* 修 改 人:  zhouliang
* 修改时间:  2016年3月4日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.yy.dal.ds.constants.StatementMode;
import org.yy.dal.executor.YYDalExecutorContext;
import org.yy.dal.nm.DbTable;
import org.yy.dal.parse.JSQLParserException;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.parser.CCJSqlParserUtil;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.route.Partition;
import org.yy.dal.util.PartitionUtil;
import org.yy.dal.util.SqlUtil;

/**
* <一句话功能简述>
* <功能详细描述>
* 
* @author  zhouliang
* @version  [版本号, 2016年3月4日]
* @since  [产品/模块版本]
*/
public class YYDalStatement extends AbsYYDalStatement implements Statement {
    
    //原始sql
    private String sql;
    
    //数据源π
    private YYDalDatasource datasource;
    
    //连接
    private YYDalConnection connection;
    
    private StatementMode model;
    
    private int reslutSetType;
    
    private int resultSetConcurrency;
    
    private int resultSetHoldability;
    
    public YYDalStatement(YYDalDatasource datasource, YYDalConnection connection) {
        this.datasource = datasource;
        this.connection = connection;
        this.model = StatementMode.BASE;
    }
    
    public YYDalStatement(YYDalDatasource datasource, YYDalConnection connection, int resultSetType,
        int resultSetConcurrency) {
        this.datasource = datasource;
        this.connection = connection;
        this.model = StatementMode.RESLUTSETTYPE_1;
        this.reslutSetType = resultSetType;
        this.resultSetConcurrency = resultSetConcurrency;
    }
    
    public YYDalStatement(YYDalDatasource datasource, YYDalConnection connection, int resultSetType,
        int resultSetConcurrency, int resultSetHoldability) {
        this.datasource = datasource;
        this.connection = connection;
        this.model = StatementMode.RESLUTSETTYPE_2;
        this.reslutSetType = resultSetType;
        this.resultSetConcurrency = resultSetConcurrency;
        this.resultSetHoldability = resultSetHoldability;
    }
    
    /** {@inheritDoc} */
    @Override
    public ResultSet executeQuery(String sql)
        throws SQLException {
        return null;
    }
    
    /** {@inheritDoc} */
    @Override
    public int executeUpdate(String sql)
        throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }
    
    /** {@inheritDoc} */
    @Override
    public void close()
        throws SQLException {
        return;
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean execute(String sql)
        throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
    
    /** {@inheritDoc} */
    @Override
    public Connection getConnection()
        throws SQLException {
        return this.connection;
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isClosed()
        throws SQLException {
        return false;
    }
    
    protected YYDalExecutorContext initExecutorParam()
        throws JSQLParserException, SQLException {
        //Step 1, sql参数填充，以便分析sql时能正常取出分表字段的值
        org.yy.dal.parse.statement.Statement statement = CCJSqlParserUtil.parse(this.sql);
        
        //Step 2, 获取最终选择的实例及分表信息
        Map<String, Table> tables = SqlUtil.getTables(statement); //取语句中用到的表
        Map<String, Expression> whereColumns = SqlUtil.getWhere(statement); //取语句中where的列
        DbTable dbtable = PartitionUtil.partitionTable(tables, datasource.getDbnodeManager()); //取到第一个分库表信息，TODO 有bug,不能是第一个，要取出所有的，并取出参数值范围最小的那个
        Partition partition = PartitionUtil.partition(tables, whereColumns, datasource.getDbnodeManager());
        
        //Step 3, 获取当前sql所要的数据库连接
        List<Connection> conns = fetchConnection(partition, connection);
        
        //Step 4, 执行并返回结果 
        YYDalExecutorContext param = new YYDalExecutorContext(conns, null, this.sql, statement, dbtable, partition);
        return param;
    }
    
    /**
    * @return 返回 model
    */
    public StatementMode getModel() {
        return model;
    }
    
    /**
    * @return 返回 sql
    */
    public String getSql() {
        return sql;
    }
    
    /**
    * @return 返回 reslutSetType
    */
    public int getReslutSetType() {
        return reslutSetType;
    }
    
    /**
    * @return 返回 resultSetConcurrency
    */
    public int getResultSetConcurrency() {
        return resultSetConcurrency;
    }
    
    /**
    * @return 返回 resultSetHoldability
    */
    public int getResultSetHoldability() {
        return resultSetHoldability;
    }
    
}
