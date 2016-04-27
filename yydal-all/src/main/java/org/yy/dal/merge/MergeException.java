/*
* 文 件 名:  MergeException.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  merge层异常
* 修 改 人:  zhouliang
* 修改时间:  2016年4月17日
* 修改内容:  <修改内容>
*/
package org.yy.dal.merge;

/**
* merge层异常
* 
* @author  zhouliang
* @version  [版本号, 2016年4月17日]
* @since  [产品/模块版本]
*/
@SuppressWarnings("serial")
public class MergeException extends RuntimeException {
    
    public MergeException(String msg) {
        super(msg);
    }
    
    public MergeException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
