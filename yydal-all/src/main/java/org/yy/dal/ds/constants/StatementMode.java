/*
* 文 件 名:  StatementMode.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  创建statement对象的模式
* 修 改 人:  zhouliang
* 修改时间:  2016年3月4日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds.constants;

/**
* 创建statement对象的模式
* 
* @author  zhouliang
* @version  [1.0, 2016年3月4日]
* @since  [yy-sdal/1.0]
*/
public enum StatementMode {
    BASE, //createStatement() 
    RESLUTSETTYPE_1, //createStatement(int resultSetType, int resultSetConcurrency)
    RESLUTSETTYPE_2 //createStatement(int resultSetType, int resultSetConcurrency,int resultSetHoldability)
}
