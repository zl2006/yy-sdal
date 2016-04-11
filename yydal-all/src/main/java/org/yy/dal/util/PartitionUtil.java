/*
* 文 件 名:  PartitionUtil.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  数据为分库分表工具
* 修 改 人:  zhouliang
* 修改时间:  2016年2月19日
* 修改内容:  <修改内容>
*/
package org.yy.dal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.yy.dal.nm.DbNodeManager;
import org.yy.dal.nm.DbTable;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.route.Partition;
import org.yy.dal.route.algorithm.HashPartitionAlgorithm;
import org.yy.dal.route.algorithm.PartitionAlgorithm;

/**
* 数据库分库分表工具
* 
* @author  zhouliang
* @version  [1.0, 2016年2月19日]
* @since  [yy-sdal/1.0]
*/
public final class PartitionUtil {
    
    private static Map<String, PartitionAlgorithm> algorithms = new HashMap<String, PartitionAlgorithm>();
    
    //初始化内置的hash算法
    static {
        register(new HashPartitionAlgorithm());
    }
    
    //注册分库分表路由算法
    public static void register(PartitionAlgorithm partitionAlgorigthm) {
        algorithms.put(partitionAlgorigthm.getName(), partitionAlgorigthm);
    }
    
    /**
     * 根据指定sql获取分区， 详见PartitionAlgorithm类。尽量将
     */
    public static Partition partition(Map<String, Table> tables, Map<String, Where> whereColumns,
        DbNodeManager nodeManager) {
        
        Partition partition = null;
        
        //Step 1 : 当sql中没有表或没有定义分表规则时，返回null
        Map<String, DbTable> tableRules = nodeManager.dbTables();
        if (tables == null || tables.size() == 0 || tableRules == null || tableRules.size() == 0) {
            return null;
        }
        
        //Step 2 : 遍历所有分库表，获取第一个匹配上的分库分表信息 
        Set<String> tableNames = tables.keySet();
        for (String tableName : tableNames) {
            tableName = tableName.replace("`", "");
            DbTable tableRule = tableRules.get(tableName);
            if (tableRule != null) {
                String ruleName = tableRule.getRuleName();
                Partition temp =
                    algorithms.get(ruleName).calculate(tables.get(tableName), whereColumns, tableRule, nodeManager);//使用指定分表及where条件来选择数据库实例及分表
                if( temp == null){//尽量找到最佳匹配分表
                    continue;
                }
                if ( partition == null ){
                    partition = temp;
                    continue;
                }
                if( temp.getInstNumber() > partition.getInstNumber()){
                    partition = temp;
                    break;
                }
            }
        }
        return partition;
        
    }
}
