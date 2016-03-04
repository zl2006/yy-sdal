/*
* 文 件 名:  DbType.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  数据库类型
* 修 改 人:  zhouliang
* 修改时间:  2016年3月3日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds.constants;

/**
* 数据库类型
* 
* @author  zhouliang
* @version  [1.0, 2016年3月3日]
* @since  [yy-sdal/1.0]
*/
public enum DBType {
    
    MYSQL("mysql");
    
    private String type;
    
    private DBType(String type) {
        this.type = type;
    }
    
    public String value() {
        return this.type;
    }
}
