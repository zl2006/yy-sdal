/*
* 文 件 名:  DbInstance.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  数据库实例信息
* 修 改 人:  zhouliang
* 修改时间:  2016年2月16日
* 修改内容:  <修改内容>
*/
package org.yy.dal.nm;

/**
* 描述数据库实例信息
* 
* @author  zhouliang
* @version  [1.0, 2016年2月16日]
* @since  [yy-sdal/1.0]
*/
public class DbInstance {
    
    /**
     * 实例节点描述
     */
    private String dbinstanceDesc;
    
    public DbInstance(String dbinstanceDesc) {
        this.dbinstanceDesc = dbinstanceDesc;
    }
    
    /**
    * @return 返回 dbinstanceDesc
    */
    public String getDbinstanceDesc() {
        return dbinstanceDesc;
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "DbInstance [dbinstanceDesc=" + dbinstanceDesc + "]";
    }
    
}
