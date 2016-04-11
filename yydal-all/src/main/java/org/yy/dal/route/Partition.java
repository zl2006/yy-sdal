/*
* 文 件 名:  Partition.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  <描述>
* 修 改 人:  zhouliang
* 修改时间:  2016年3月1日
* 修改内容:  <修改内容>
*/
package org.yy.dal.route;

import org.yy.dal.nm.DbTable;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.schema.Table;

/**
* 分库分表结果
* 
* @author  zhouliang
* @version  [1.0, 2016年3月1日]
* @since  [yy-sdal/1.0]
*/
public class Partition {
    
    //第几个实例
    private int instNumber;
    
    //第几个分表
    private int tableNumber;
    
    //分表信息
    private Table table;
    
    //列
    private Column column;
    
    //值
    private Object columnValue;
    
    //路由规则 
    private DbTable tableRule;
    
    public Partition(int instNumber, int tableNumber, Table table, Column column, Object columnValue, DbTable tableRule) {
        this.instNumber = instNumber;
        this.tableNumber = tableNumber;
        this.table = table;
        this.column = column;
        this.columnValue = columnValue;
        this.tableRule = tableRule;
    }
    
    /**
    * @return 返回 instNumber
    */
    public int getInstNumber() {
        return instNumber;
    }
    
    /**
    * @return 返回 tableNumber
    */
    public int getTableNumber() {
        return tableNumber;
    }
    
    /**
    * @return 返回 table
    */
    public Table getTable() {
        return table;
    }
    
    /**
    * @return 返回 column
    */
    public Column getColumn() {
        return column;
    }
    
    /**
    * @return 返回 columnValue
    */
    public Object getColumnValue() {
        return columnValue;
    }
    
    /**
    * @return 返回 tableRule
    */
    public DbTable getTableRule() {
        return tableRule;
    }
    
    /**
    * @param 对tableRule进行赋值
    */
    public void setTableRule(DbTable tableRule) {
        this.tableRule = tableRule;
    }
    
}
