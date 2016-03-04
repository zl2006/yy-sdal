/*
* 文 件 名:  ParameterUtil.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  参数处理工具
* 修 改 人:  zhouliang
* 修改时间:  2016年2月23日
* 修改内容:  <修改内容>
*/
package org.yy.dal.util;

import java.util.ArrayList;
import java.util.List;

import org.yy.dal.ds.YYDalParameter;

/**
* 参数处理工具
* 
* @author  zhouliang
* @version  [1.0, 2016年2月23日]
* @since  [yy-sdal/1.0]
*/
public final class ParameterUtil {
    
    /**
     * 对sql填充参数并返回,暂时只需要处理整数及字符串类型
     * 
     * @param sql sql语句
     * @param params 参数 
     * @return
     */
    public static String fillParam(String sql, List<YYDalParameter> params) {
        List<String> paramStrs = new ArrayList<String>();
        for (YYDalParameter item : params) {
            if (item == null) {
                paramStrs.add("''");
                continue;
            }
            paramStrs.add(item.getValue());
        }
        sql = sql.replace("?", "%s");
        sql = String.format(sql, paramStrs.toArray());
        return sql;
    }
    
    /** 
     * 计算sql中的参数 
     * @param sql 
    * @return counter 
    */
    public static int countParams(String sql) {
        StringBuilder sb = new StringBuilder(sql);
        int counter = 0;
        int posi = -1;
        do {
            posi = sb.indexOf("?", posi + 1);
            if (posi >= 0) {
                counter++;
            }
            else {
                break;
            }
        } while (true);
        
        return counter;
    }
    
    public static void main(String[] args) {
        List<YYDalParameter> params = new ArrayList<YYDalParameter>();
        
        params.add(new YYDalParameter("setInt", 123));
        params.add(new YYDalParameter("setString", "DF23dsRe"));
        
        String sql = "select * from TB_WLJ_QRCODE where id = ? and QRCODE=?";
        
        System.out.println(countParams(sql));
        
        System.out.println(fillParam(sql, params));
    }
}
