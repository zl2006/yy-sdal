/*
* 文 件 名:  MysqlDbParse.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  mysql分库分表定义解析
* 修 改 人:  zhouliang
* 修改时间:  2016年2月17日
* 修改内容:  <修改内容>
*/
package org.yy.dal.nm.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yy.dal.nm.DbInstance;
import org.yy.dal.nm.DbNode;
import org.yy.dal.nm.DbNodeManager;
import org.yy.dal.nm.DbTable;
import org.yy.dal.nm.DefaultNodeManager;

/**
* mysql分库分表定义解析
*    * 节点描述定义,
     * jdbc:mysql://192.168.1.[1,2]:3306:useradmin_inst_[1-3];jdbc:mysql://192.168.1.[3-6]:3306:useradmin_inst_[1-3]
     * 节点描述信息， jdbc:mysql://192.168.1.[1,3]:3306:useradmin_inst_[1-3]
     * 相当于：
     * jdbc:mysql://192.168.1.1:3306:useradmin_inst_1
     * jdbc:mysql://192.168.1.1:3306:useradmin_inst_2
     * jdbc:mysql://192.168.1.1:3306:useradmin_inst_3
     * jdbc:mysql://192.168.1.3:3306:useradmin_inst_1
     * jdbc:mysql://192.168.1.3:3306:useradmin_inst_2
     * jdbc:mysql://192.168.1.3:3306:useradmin_inst_3
     * 
     * 分表定义
     * 例如：user_[6]:hash(user_id)
     * 实际表名为user_1   user_2 ... user_6
     * 如果是单表（采用表分区时）可以定义为user_[1]:hash(user_id)
     * 实际表名为user,不在有后辍
* 
* @author  zhouliang
* @version  [1.0, 2016年2月17日]
* @since  [yy-sdal/1.0]
*/
public class MysqlDbParse implements DbParse {
    
    private static Logger logger = LoggerFactory.getLogger(MysqlDbParse.class);
    
    //节点分隔符
    private static String DBNODE_SPLIT = ";";
    
    //分表中的分隔符
    private static String DBTABLE_SPLIT = ":";
    
    /** {@inheritDoc} */
    @Override
    public void parse(DbNodeManager dbNodeManager) {
        if (!(dbNodeManager instanceof DefaultNodeManager)) {
            throw new RuntimeException("不能解析非mysql节点定义");
        }
        
        DefaultNodeManager mysqlDbNodeManager = (DefaultNodeManager)dbNodeManager;
        parseDbNode(mysqlDbNodeManager);
        
    }
    
    //初始化节点
    protected void parseDbNode(DefaultNodeManager nodeManager) {
        String dbnodeListDesc = nodeManager.getDbnodeListDesc();
        
        //Step1: 分拆节点配置描述
        String[] dbnodeDescs = dbnodeListDesc.split(DBNODE_SPLIT);
        
        //Step2:解析节点
        for (String dbnodeItem : dbnodeDescs) {
            if (dbnodeItem == null || "".equals(dbnodeItem.trim())) {
                continue;
            }
            
            //Step2.1:解析出节点中的变量项jdbc:mysql://192.168.1.[1,3]:3306:useradmin_inst_[1-3], 如[1,3],[1-3]
            Pattern p = Pattern.compile("\\[\\d*[,-]*\\d*]");
            Matcher m = p.matcher(dbnodeItem);
            if (logger.isDebugEnabled()) {
                logger.info("解析节点:" + dbnodeItem);
            }
            String firstvarstr = null;
            if (m.find()) { //获取变量项一
                firstvarstr = m.group();
                logger.info("变量一:" + firstvarstr);
            }
            String secondvarstr = null; //获取变量项二
            if (m.find()) {
                secondvarstr = m.group();
                logger.info("变量二:" + secondvarstr);
            }
            
            //Step2.2:替换变量项,jdbc:mysql://192.168.1.[1,3]:3306:useradmin_inst_[1-3]变成jdbc:mysql://192.168.1.1:3306:useradmin_inst_1
            dbnodeItem = dbnodeItem.replaceAll("\\[\\d*[,-]*\\d*]", "%s"); //Pattern.compile("\\[\\d*[,-]*\\d*]")
            String[] firstVarGroup = processNodeNumVar(firstvarstr);
            String[] secondVarGroup = processNodeNumVar(secondvarstr);
            
            //不存在变量的情况
            if (firstVarGroup == null) {
                DbNode dbnode = processNode(nodeManager, dbnodeItem); //生成节点,如:jdbc:mysql://192.168.1.1:3306
                DbInstance tempInstance = new DbInstance(dbnodeItem); //生成实例,如:jdbc:mysql://192.168.1.1:3306:useradmin_inst_1
                dbnode.addDbInstance(tempInstance);
                nodeManager.dbInstances().add(tempInstance);
                continue;
            }
            
            for (String firstVarItem : firstVarGroup) {
                //只存在一个变量的情况
                if (secondVarGroup == null) {
                    DbNode dbnode = processNode(nodeManager, String.format(dbnodeItem, firstVarItem));
                    DbInstance tempInstance = new DbInstance(String.format(dbnodeItem, firstVarItem));
                    dbnode.addDbInstance(tempInstance);
                    nodeManager.dbInstances().add(tempInstance);
                    continue;
                }
                for (String secondVarItem : secondVarGroup) {
                    DbNode dbnode = processNode(nodeManager, String.format(dbnodeItem, firstVarItem, secondVarItem));
                    DbInstance tempInstance = new DbInstance(String.format(dbnodeItem, firstVarItem, secondVarItem));
                    dbnode.addDbInstance(tempInstance);
                    nodeManager.dbInstances().add(tempInstance);
                    continue;
                }
                
            }
        }
    }
    
    //初始化分表
    protected void parseDbTable(DefaultNodeManager mysqlDbNodeManager) {
        
        for (String tableItem : mysqlDbNodeManager.getTableListDescs()) {
            int temp = tableItem.indexOf(DBTABLE_SPLIT);
            String tableDesc = tableItem.substring(0, temp);
            String ruleDesc = tableItem.substring(temp + 1, tableItem.length());
            String tableName = tableDesc.substring(0, tableDesc.lastIndexOf("_"));
            Pattern p = Pattern.compile("\\[\\d*[,-]*\\d*]");
            Matcher m = p.matcher(tableDesc);
            m.find();
            int tableNum = Integer.valueOf(m.group().replaceAll("[\\[\\]]", ""));
            mysqlDbNodeManager.dbTables().put(tableName, new DbTable(tableName, tableNum, ruleDesc));
        }
        
    }
    
    //处理生成节点,jdbc:mysql://192.168.1.1:3306
    private DbNode processNode(DefaultNodeManager mysqlDbNodeManager, String dbnodeItem) {
        String[] temp = dbnodeItem.split(":");
        StringBuilder sb = new StringBuilder();
        sb.append(temp[0]).append(":");
        sb.append(temp[1]).append(":");
        sb.append(temp[2]).append(":");
        sb.append(temp[3]);
        DbNode dbNode = mysqlDbNodeManager.dbNodes().get(sb.toString());
        if (dbNode == null) {
            dbNode = new DbNode(sb.toString());
            mysqlDbNodeManager.dbNodes().put(sb.toString(), dbNode);
        }
        return dbNode;
    }
    
    //处理节点中的数字变量项
    private String[] processNodeNumVar(String varStr) {
        String[] varGroup = null;
        if (varStr != null) {
            varStr = varStr.replaceAll("[\\[\\]]", ""); //删除中括号，由[1,2]变成1,2
            varGroup = varStr.split("[,-]");
            
            if (varStr.indexOf("-") > 0) { //区间表示法的特殊处理
                int start = Integer.valueOf(varGroup[0]);
                int end = Integer.valueOf(varGroup[1]);
                varGroup = new String[end - start + 1];
                for (int i = start, j = 0; i <= end; ++i, ++j) {
                    varGroup[j] = "" + i;
                }
            }
        }
        return varGroup;
    }
    
}
