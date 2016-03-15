/*
* 文 件 名:  Executor.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  sql执行器
* 修 改 人:  zhouliang
* 修改时间:  2016年3月4日
* 修改内容:  <修改内容>
*/
package org.yy.dal.executor;

import org.yy.dal.ds.YYDalParameter;

/**
* sql执行器
* 
* @author  zhouliang
* @version  [1.0, 2016年3月4日]
* @since  [yy-sdal/1.0]
*/
public interface Executor {
    
    /**
     * 执行
     * @param parameter SQL参数 
     */
    public Object execute(YYDalParameter parameter);
}
