/*
* 文 件 名:  DbWhere.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  SQL中的where条件或insert中的列描述
* 修 改 人:  zhouliang
* 修改时间:  2016年4月10日
* 修改内容:  <修改内容>
*/
package org.yy.dal.util;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.schema.Column;

/**
* SQL中的where条件或insert中的列描述
* 
* @author  zhouliang
* @version  [1.0, 2016年2月16日]
* @since  [yy-sdal/1.0]
*/
public class Where {
    
    //列名
    private Column column;
    
    //列值
    private Expression columnValue;
    
    /** 
    <默认构造函数>
    */
    public Where(Column column, Expression columnValue) {
        this.column = column;
        this.columnValue = columnValue;
    }
    
    /**
    * @return 返回 column
    */
    public Column getColumn() {
        return column;
    }
    
    /**
    * @return 返回 columnValue
    */
    public Expression getColumnValue() {
        return columnValue;
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "DbWhere [column=" + column + ", columnValue=" + columnValue + "]";
    }
    
}
