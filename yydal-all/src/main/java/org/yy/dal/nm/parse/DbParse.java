/*
* 文 件 名:  DbNodeParse.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  解析分库分表定义
* 修 改 人:  zhouliang
* 修改时间:  2016年2月17日
* 修改内容:  <修改内容>
*/
package org.yy.dal.nm.parse;

import org.yy.dal.nm.manage.DbNodeManager;

/**
* 解析分库分表定义
* 
* @author  zhouliang
* @version  [1.0, 2016年2月17日]
* @since  [yy-sdal/1.0]
*/
public interface DbParse {
    
    /**
     * 解析分库分表定义
     */
    public void parse(DbNodeManager dbNodeManager);
}
