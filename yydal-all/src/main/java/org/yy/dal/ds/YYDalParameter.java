/*
* 文 件 名:  YYDalParameter.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  参数
* 修 改 人:  zhouliang
* 修改时间:  2016年2月23日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds;

/**
* 参数
* 
* @author  zhouliang
* @version  [参数 , 2016年2月23日]
* @since  [yy-sdal/1.0]
*/
public class YYDalParameter {
    
    //方法名
    private String property;
    
    private Object param1;
    
    private Object param2;
    
    private Object param3;
    
    private Object param4;
    
    private int num;
    
    public YYDalParameter(String property, Object param1) {
        this.property = property;
        this.param1 = param1;
        num = 1;
    }
    
    public YYDalParameter(String method, Object param1, Object param2) {
        this(method, param1);
        this.param2 = param2;
        num = 2;
    }
    
    public YYDalParameter(String method, Object param1, Object param2, Object param3) {
        this(method, param1, param2);
        this.param3 = param3;
        num = 3;
    }
    
    public YYDalParameter(String method, Object param1, Object param2, Object param3, Object param4) {
        this(method, param1, param2, param3);
        this.param4 = param4;
        num = 4;
    }
    
    /**
    * @return 返回 method
    */
    public String getProperty() {
        return property;
    }
    
    /**
    * @return 返回 param1
    */
    public Object getParam1() {
        return param1;
    }
    
    /**
    * @return 返回 param2
    */
    public Object getParam2() {
        return param2;
    }
    
    /**
    * @return 返回 param3
    */
    public Object getParam3() {
        return param3;
    }
    
    /**
    * @return 返回 param4
    */
    public Object getParam4() {
        return param4;
    }
    
    /**
    * @return 返回 num
    */
    public int getNum() {
        return num;
    }
    
    //暂时只支持String ,int , Long
    public String getValue() {
        if (param1 == null) {
            return "''";
        }
        if (param1 instanceof String) {
            return "'" + (String)param1 + "'";
        }
        else if (param1 instanceof Long) {
            return ((Long)param1).longValue() + "";
        }
        else if (param1 instanceof Integer) {
            return ((Integer)param1).intValue() + "";
        }
        else {
            return "''";
        }
        
    }
    
}
