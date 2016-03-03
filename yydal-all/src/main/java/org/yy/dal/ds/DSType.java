/*
* 文 件 名:  DsType.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  数据源类型
* 修 改 人:  zhouliang
* 修改时间:  2016年3月3日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds;

/**
* 数据源类型
* 
* @author  zhouliang
* @version  [1.0, 2016年3月3日]
* @since  [yy-sdal/1.0]
*/
public enum DSType {
    
    C3P0("c3p0"), DBCP("dbcp");
    
    private String type;
    
    private DSType(String type) {
        this.type = type;
    }
    
    public String value() {
        return this.type;
    }
}
