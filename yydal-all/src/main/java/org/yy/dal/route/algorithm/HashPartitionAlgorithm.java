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
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.yy.dal.nm.DbNodeManager;
import org.yy.dal.nm.DbTable;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.route.Partition;

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
    public Partition calculate(Map<String, Table> tables, Map<String, Expression> whereColumns,
        DbNodeManager nodeManager) {
        
        Map<String, DbTable> dbtables = nodeManager.dbTables();
        
        //sql中无分表表名，或无分表定义时，返回null并使用默认数据库实例操作
        if (tables == null || tables.size() == 0 || dbtables == null || dbtables.size() == 0) {
            return null;
        }
        
        //使用所有实例，无分表字段时
        if (whereColumns == null || whereColumns.size() == 0) {
            return new Partition(-1, -1);
        }
        
        Set<String> tableNames = tables.keySet();
        try {
            for (String tableName : tableNames) {
                tableName = tableName.replace("`", "");
                if (dbtables.get(tableName) != null) { //为分库表时
                    DbTable dbtable = dbtables.get(tableName);
                    Table table = tables.get(tableName);
                    String columnName = partitionColumnName(dbtable.getRuleDesc());
                    Object value =
                        whereColumns.get((table.getAlias() != null ? table.getAlias().getName().toUpperCase() + "."
                            : "") + columnName);
                    if (value == null) {
                        continue;
                    }
                    value = BeanUtils.getProperty(value, "value");
                    return calculate(value, tableName, nodeManager);
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        return null;
    }
    
    //获取分表字段名称
    protected String partitionColumnName(String ruleDesc) {
        int start = ruleDesc.indexOf("(") + 1;
        int end = ruleDesc.indexOf(")");
        return ruleDesc.substring(start, end).toString();
    }
    
    //要支持多种类型，暂时支持string, integer, long
    private Partition calculate(Object value, String tableName, DbNodeManager nodeManager) {
        
        if (value instanceof String || value instanceof Integer || value instanceof Long) {
            int hashCode = Math.abs(value.hashCode());
            
            int partitionTables =
                nodeManager.dbInstances().size() * nodeManager.dbTables().get(tableName).getTableNum();
            
            int result = hashCode % partitionTables; //算出数据应该分在哪个总表上
            
            int instanceNum = result / nodeManager.dbInstances().size(); //算出数据在哪个实例上
            int tableNum = result % nodeManager.dbInstances().size(); //算出数据在哪个实例的哪个分表上
            
            return new Partition(instanceNum, tableNum);
        }
        else {
            throw new RuntimeException("不支持的分库字段数据类型：" + value.getClass());
        }
    }
    
}