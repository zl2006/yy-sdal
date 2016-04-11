/*
* 文 件 名:  HashPartitionAlgorithm.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  Hash分库分表算法
* 修 改 人:  zhouliang
* 修改时间:  2016年2月19日
* 修改内容:  <修改内容>
*/
package org.yy.dal.route.algorithm;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.yy.dal.nm.DbNodeManager;
import org.yy.dal.nm.DbTable;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.route.Partition;
import org.yy.dal.util.SqlUtil;
import org.yy.dal.util.Where;

/**
* Hash分库分表算法
* 
* @author  zhouliang
* @version  [版本号, 2016年2月19日]
* @since  [产品/模块版本]
*/
public class HashPartitionAlgorithm implements PartitionAlgorithm {
    
    /** {@inheritDoc} */
    @Override
    public Partition calculate(Table table, Map<String, Where> whereColumns, DbTable tableRule,
        DbNodeManager nodeManager) {
        
        //Step 1 : 分表空时，返回null。此时使用默认数据库实例操作DML
        if (table == null) {
            return null;
        }
        
        //Step 2 : DML有分表但无分表字段时,使用数据库中所有的实例
        if (whereColumns == null || whereColumns.size() == 0) {
            return new Partition(-1, -1, table, null, null,tableRule);
        }
        
        //Step 3 : 获取分表规则中的列名及值,二种列表达方式下取值
        String columnName = partitionColumnName(tableRule.getRuleDesc());
        Where where =
            whereColumns.get((table.getAlias() != null ? table.getAlias().getName().toUpperCase() + "." : "")
                + columnName);
        if (where == null) {
            where = whereColumns.get(SqlUtil.orgiTableName(table) + "." + columnName);
        }
        if (where == null) {
            return new Partition(-1, -1, table, null, null,tableRule);
        }
        return calculate(where, table, nodeManager,tableRule);
    }
    
    //获取分表字段名称
    private String partitionColumnName(String ruleDesc) {
        int start = ruleDesc.indexOf("(") + 1;
        int end = ruleDesc.indexOf(")");
        return ruleDesc.substring(start, end).toString();
    }
    
    //要支持多种类型，暂时支持string, integer, long
    private Partition calculate(Where where, Table table, DbNodeManager nodeManager, DbTable tableRule) {
        String tableName = SqlUtil.orgiTableName(table).toUpperCase();
        Object value;
        try {
            value = BeanUtils.getProperty(where.getColumnValue(), "value");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        if (value instanceof String || value instanceof Integer || value instanceof Long) {
            int hashCode = Math.abs(value.hashCode());
            
            int partitionTables =
                nodeManager.dbInstances().size() * nodeManager.dbTables().get(tableName).getTableNum();
            
            int result = hashCode % partitionTables; //算出数据应该分在哪个总表上
            
            int instanceNum = result / nodeManager.dbInstances().size(); //算出数据在哪个实例上
            int tableNum = result % nodeManager.dbInstances().size(); //算出数据在哪个实例的哪个分表上
            
            return new Partition(instanceNum, tableNum, table, where.getColumn(), where.getColumnValue(),tableRule);
        }
        else {
            throw new RuntimeException("不支持的分库字段数据类型：" + value.getClass());
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public String getName() {
        return "hash";
    }
    
}