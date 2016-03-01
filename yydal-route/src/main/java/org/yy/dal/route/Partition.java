/*
* 文 件 名:  Partition.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  <描述>
* 修 改 人:  zhouliang
* 修改时间:  2016年3月1日
* 修改内容:  <修改内容>
*/
package org.yy.dal.route;

/**
* 分库分表结果
* 
* @author  zhouliang
* @version  [1.0, 2016年3月1日]
* @since  [yy-sdal/1.0]
*/
public class Partition {
    
    //第几个实例
    private int instNumber;
    
    //第几个分表
    private int tableNumber;
    
    public Partition(int instNumber, int tableNumber) {
        this.instNumber = instNumber;
        this.tableNumber = tableNumber;
    }
    
    /**
    * @return 返回 instNumber
    */
    public int getInstNumber() {
        return instNumber;
    }
    
    /**
    * @return 返回 tableNumber
    */
    public int getTableNumber() {
        return tableNumber;
    }
    
}
