/*
* 文 件 名:  Router.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  路由器
* 修 改 人:  zhouliang
* 修改时间:  2016年2月19日
* 修改内容:  <修改内容>
*/
package org.yy.dal.route.algorithm;

import java.util.Map;

import org.yy.dal.nm.DbNodeManager;
import org.yy.dal.nm.DbTable;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.route.Partition;
import org.yy.dal.util.Where;

/**
* 分区算法
* 
* @author  zhouliang
* @version  [1.0, 2016年2月19日]
* @since  [yy-sdal/1.0]
*/
public interface PartitionAlgorithm {
    
    /**
     * 算法名称
     */
    public String getName();
    
    /**
     * 分区计算,计算使用哪个实例哪个表
     * 
     * @param table 分库表
     * @param whereColums where中的列或insert中的列
     * @param tableRule 表路由规则 
     * @return 共两个值，第一个返回第几个数据库实例，第二个值返回第几个分表。
     * 返回空表示无分库分表信息，使用默认数据库实例。
     * 返回[-1,-1]时，表示有分表但没带路由规则,使用所有定义的分库分表数据库实例。
     * 返回[2,3]时，表示使用第3个实例，第4个分表
     */
    public Partition calculate(Table table, Map<String, Where> whereColumns, DbTable tableRule,
        DbNodeManager nodeManager);
}
