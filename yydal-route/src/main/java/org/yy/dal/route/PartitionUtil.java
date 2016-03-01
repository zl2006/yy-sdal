/*
* 文 件 名:  PartitionUtil.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  数据为分库分表工具
* 修 改 人:  zhouliang
* 修改时间:  2016年2月19日
* 修改内容:  <修改内容>
*/
package org.yy.dal.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.yy.dal.nm.DbTable;
import org.yy.dal.nm.manage.DbNodeManager;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.route.partition.HashPartitionAlgorithm;
import org.yy.dal.route.partition.PartitionAlgorithm;

/**
* 数据库分库分表工具
* 
* @author  zhouliang
* @version  [1.0, 2016年2月19日]
* @since  [yy-sdal/1.0]
*/
public final class PartitionUtil {
    
    private static Map<String, PartitionAlgorithm> algorithms = new HashMap<String, PartitionAlgorithm>();
    
    //注册算法
    static {
        algorithms.put("hash", new HashPartitionAlgorithm());
    }
    
    /**
     * 取匹配到的第一个分库表
     */
    public static DbTable partitionTable(Map<String, Table> tables, DbNodeManager nodeManager) {
        Map<String, DbTable> dbtables = nodeManager.dbTables();
        //Step 1 : 当sql中没有表或没有定义分表时，返回null
        if (tables == null || tables.size() == 0 || dbtables == null || dbtables.size() == 0) {
            return null;
        }
        Set<String> tableNames = tables.keySet();
        
        for (String tableName : tableNames) {
            if (dbtables.get(tableName) != null) {
                return dbtables.get(tableName);
            }
        }
        return null;
    }
    
    /**
     * 根据指定sql获取分区， 详见PartitionAlgorithm类
     */
    public static Partition partition(Map<String, Table> tables, Map<String, Expression> whereColumns,
        DbNodeManager nodeManager) {
        
        DbTable dbtable = partitionTable(tables, nodeManager);
        
        //Step 1 : 当sql中没有表或没有定义分表时，返回null, 使用默认数据库实例操作
        if (dbtable == null) {
            return null;
        }
        
        //Step 2 : 使用指定分表及where条件来选择数据库实例及分表
        String ruleName = dbtable.getRuleName();
        return algorithms.get(ruleName).calculate(tables, whereColumns, nodeManager);
        
    }
}
