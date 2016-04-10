/*
* 文 件 名:  NodeManageParse.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  节点管理器
* 修 改 人:  zhouliang
* 修改时间:  2016年2月16日
* 修改内容:  <修改内容>
*/
package org.yy.dal.nm;

import java.util.List;
import java.util.Map;

/**
* 节点管理器
* 
* @author  zhouliang
* @version  [1.0, 2016年2月16日]
* @since  [yy-sdal/1.0]
*/
public interface DbNodeManager {
    
    /**
     * 所有数据库实例
     */
    public List<DbInstance> dbInstances();
    
    /**
     * 所有数据库节点
     */
    public Map<String, DbNode> dbNodes();
    
    /**
     * 所有数据库分表信息
     */
    public Map<String, DbTable> dbTables();
    
    /**
     * 节点与实例定义
     */
    public String getDbnodeDef();
    
    /**
     * 数据库分表及路由规则
     */
    public List<String> getTableRuleDefs();
    
}