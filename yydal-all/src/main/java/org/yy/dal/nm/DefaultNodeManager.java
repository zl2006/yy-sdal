/*
* 文 件 名:  MysqlNodeManager.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  mysql节点管理器
* 修 改 人:  zhouliang
* 修改时间:  2016年2月16日
* 修改内容:  <修改内容>
*/
package org.yy.dal.nm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yy.dal.nm.parse.DbParse;
import org.yy.dal.nm.parse.MysqlDbParse;

/**
* 节点管理器
* 
* @author  zhouliang
* @version  [1.0, 2016年2月16日]
* @since  [yy-sdal/1.0]
*/
public class DefaultNodeManager implements DbNodeManager {
    
    private static Logger logger = LoggerFactory.getLogger(DefaultNodeManager.class);
    
    /**
     * 节点及实例定义，jdbc:mysql://192.168.1.[1,2]:3306/useradmin_inst_[1-3]
     * 192.168.1.1表示节点
     * useradmin_inst_1表示实例
     */
    private String dbnodeDef;
    
    /**
     * 分表定义，包括分表及路由描述 user_[8]:hash(user_id)
     */
    private List<String> tableRuleDefs = new ArrayList<String>();
    
    /**
     * 数据库实例
     */
    private List<DbInstance> dbinstances = new ArrayList<DbInstance>();
    
    /**
     * 数据库分表
     */
    private Map<String, DbTable> dbtables = new HashMap<String, DbTable>();
    
    /**
     * 数据库节点
     */
    private Map<String, DbNode> dbnodes = new HashMap<String, DbNode>();
    
    /**
     * 构造mysql节点管理器，支持分表定义
     * 
     * @param dbnodeDef 节点定义
     * 
     * @param tableRuleDefs 分表定义
     */
    public DefaultNodeManager(DbParse dbParse, String dbnodeDef, List<String> tableRuleDefs) {
        if (logger.isDebugEnabled()) {
            logger.debug("------------------------------------------------------------");
            logger.debug("解析节点与实例：" + tableRuleDefs);
        }
        this.dbnodeDef = dbnodeDef;
        this.tableRuleDefs = tableRuleDefs;
        dbParse.parse(this);
        if (logger.isDebugEnabled()) {
            logger.debug("节点:" + dbnodes);
            logger.debug("实例:" + dbinstances);
        }
    }
    
    public String getDbnodeDef() {
        return dbnodeDef;
    }
    
    public List<String> getTableRuleDefs() {
        return tableRuleDefs;
    }
    
    /** {@inheritDoc} */
    @Override
    public List<DbInstance> dbInstances() {
        return this.dbinstances;
    }
    
    /** {@inheritDoc} */
    @Override
    public Map<String, DbNode> dbNodes() {
        return this.dbnodes;
    }
    
    /** {@inheritDoc} */
    @Override
    public Map<String, DbTable> dbTables() {
        return this.dbtables;
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "DefaultNodeManager [dbnodeDef=" + dbnodeDef + ", tableRules=" + tableRuleDefs + ", dbinstances="
            + dbinstances + ", dbtables=" + dbtables + ", dbnodes=" + dbnodes + "]";
    }
    
    public static void main(String[] args) {
        
        List<String> tableRules = new ArrayList<String>();
        tableRules.add("user_[8]:hash(user_id)");
        tableRules.add("qrcode_[8]:customFunc(qrcode_str)");
        
        DbNodeManager nm =
            new DefaultNodeManager(
                new MysqlDbParse(),
                "jdbc:mysql://192.168.1.[1,2]:3306/useradmin_inst_[1-3];jdbc:mysql://192.168.1.[4-6]:3306/useradmin_inst_[1-3];jdbc:mysql://192.168.1.[7]:3306/useradmin_inst_1",
                tableRules);
        System.out.println(nm.dbNodes());
        nm =
            new DefaultNodeManager(new MysqlDbParse(), "jdbc:mysql://192.168.1.[1-8]:3306/useradmin_inst_[1-8]",
                tableRules);
        System.out.println(nm.dbNodes());
        nm =
            new DefaultNodeManager(new MysqlDbParse(), "jdbc:mysql://192.168.1.[1-8]:3306/useradmin_inst_[1-8]",
                tableRules);
        
        System.out.println(nm.dbNodes());
    }
    
}
