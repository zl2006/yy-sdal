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
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.route.Partition;

/**
* 分区算法
* 
* @author  zhouliang
* @version  [1.0, 2016年2月19日]
* @since  [yy-sdal/1.0]
*/
public interface PartitionAlgorithm {
    
    /**
     * 分区计算
     * @param tables   sql语句中的表
     * @param whereColums where中的列或insert中的列
     * @return 共两个值，第一个返回第几个数据库实例，第二个值返回第几个分表。
     * 返回空表示无分库分表信息，应用需要查询默认实例。
     * 返回[-1,-1]时，表示有分表但没带路由规则, 应用需求查询所有实例。
     */
    public Partition calculate(Map<String, Table> tables, Map<String, Expression> whereColumns,
        DbNodeManager nodeManager);
}
