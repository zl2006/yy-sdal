/*
* 文 件 名:  ResultSetBuilder.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  ResultSet构建器
* 修 改 人:  zhouliang
* 修改时间:  2016年4月12日
* 修改内容:  <修改内容>
*/
package org.yy.dal.merge;

import java.sql.ResultSet;
import java.util.List;

import org.yy.dal.executor.YYDalExecutorContext;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.select.Limit;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.Select;
import org.yy.dal.parse.statement.select.SelectBody;

/**
* ResultSet构建器
* 
* @author  zhouliang
* @version  [版本号, 2016年4月12日]
* @since  [产品/模块版本]
*/
public class ResultSetBuilder {
    
    /**
     * 构建ResultSet
     * @param select select语句
     * @param results 结果集
     * @return
     */
    public static ResultSet build(YYDalExecutorContext executorCtx, List<ResultSet> results) {
        
        Statement statement = executorCtx.getStatement();
        
        if (statement == null) {
            throw new MergeException("statemnet is null!");
        }
        
        if (!(statement instanceof Select)) {
            throw new MergeException("statemnet is not support type!");
        }
        
        Select select = (Select)statement;
        SelectBody selectBody = select.getSelectBody();
        
        if (!(selectBody instanceof PlainSelect)) {
            throw new MergeException("statemnet is not support type!");
        }
        PlainSelect plainSelect = (PlainSelect)selectBody;
        Limit limit = plainSelect.getLimit();
        plainSelect.getOrderByElements();
        plainSelect.getGroupByColumnReferences();
        plainSelect.getSelectItems();
        return null;
    }
    
    //是否需要处理
    public boolean isAggregate(PlainSelect select) {
        return false;
    }
    
}
