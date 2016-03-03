/*
* 文 件 名:  DbNode.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  数据库节点
* 修 改 人:  zhouliang
* 修改时间:  2016年2月16日
* 修改内容:  <修改内容>
*/
package org.yy.dal.nm;

import java.util.ArrayList;
import java.util.List;

/**
* 定义数据库节点,多节点以;分隔
* jdbc:mysql://192.168.1.[1,2]:3306:useradmin_inst_[1-3];jdbc:mysql://192.168.1.[3-6]:3306:useradmin_inst_[1-3]
* 
* @author  zhouliang
* @version  [1.0, 2016年2月16日]
* @since  [yy-sdal/1.0]
*/
public class DbNode {
    
    /**
     * 节点描述，如jdbc:mysql://192.168.1.1:3306
     */
    private String dbNodeDesc;
    
    /**
     * 节点实例，如jdbc:mysql://192.168.1.1:3306:useradmin_inst_1
     */
    private List<DbInstance> dbInstances = new ArrayList<DbInstance>();
    
    public DbNode(String dbNodeDesc) {
        this.dbNodeDesc = dbNodeDesc;
    }
    
    public void addDbInstance(DbInstance dbInstance) {
        this.dbInstances.add(dbInstance);
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "DbNode [dbNodeDesc=" + dbNodeDesc + ", dbInstances=" + dbInstances + "]";
    }
    
}
