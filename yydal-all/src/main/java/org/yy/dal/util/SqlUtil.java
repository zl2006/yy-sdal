/*
* 文 件 名:  SqlUtil.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  sql处理工具类
* 修 改 人:  zhouliang
* 修改时间:  2016年2月18日
* 修改内容:  <修改内容>
*/
package org.yy.dal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.yy.dal.parse.JSQLParserException;
import org.yy.dal.parse.expression.BinaryExpression;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.operators.relational.EqualsTo;
import org.yy.dal.parse.expression.operators.relational.ExpressionList;
import org.yy.dal.parse.expression.operators.relational.OldOracleJoinBinaryExpression;
import org.yy.dal.parse.parser.CCJSqlParserUtil;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.delete.Delete;
import org.yy.dal.parse.statement.insert.Insert;
import org.yy.dal.parse.statement.select.Join;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.Select;
import org.yy.dal.parse.statement.update.Update;

/**
* sql处理工具类
* 
* @author  zhouliang
* @version  [1.0, 2016年2月18日]
* @since  [yy-sdal/1.0]
*/
public final class SqlUtil {
    
    /**
     * 获取sql中的数据库分表
     */
    public static Map<String, Table> getTables(Statement statement) {
        if (statement instanceof Select && ((Select)statement).getSelectBody() instanceof PlainSelect) {
            PlainSelect ps = (PlainSelect)((Select)statement).getSelectBody();
            return getTables(ps);
        }
        else if (statement instanceof Update) {
            return getTables(((Update)statement).getTables());
        }
        else if (statement instanceof Insert) {
            List<Table> tables = new ArrayList<Table>();
            tables.add(((Insert)statement).getTable());
            return getTables(tables);
        }
        else if (statement instanceof Delete) {
            List<Table> tables = new ArrayList<Table>();
            tables.add(((Delete)statement).getTable());
            return getTables(tables);
        }
        else {
            return new HashMap<String, Table>();
        }
    }
    
    /**
     * 获取sql中的数据库分表
     */
    public static Map<String, Table> getTables(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            return getTables(statement);
        }
        catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 获取分表字段, 不支持子查询, 右值支持data,long,Timestamp,string等
     * 
     * 注：返回数据为字段名+值，字段名的形式为名称或别名.名称, 如:QRCODE或a.QRCODE
     */
    public static Map<String, Expression> getWhere(Statement statement) {
        try {
            if (statement instanceof Select && ((Select)statement).getSelectBody() instanceof PlainSelect) { //解析select语句
                PlainSelect ps = (PlainSelect)((Select)statement).getSelectBody();
                return getWhere(ps.getWhere());
            }
            else if (statement instanceof Update) {
                return getWhere(((Update)statement).getWhere());
            }
            else if (statement instanceof Insert) {
                return getInsertColumn((Insert)statement);
            }
            else if (statement instanceof Delete) {
                return getWhere(((Delete)statement).getWhere());
            }
            else {
                return new HashMap<String, Expression>();
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * 获取条件字段,不支持子查询, 右值支持data,long,Timestamp,string等
     * 
     * 注：返回数据为字段名+值，字段名的形式为名称或别名.名称, 如:QRCODE或a.QRCODE
     */
    public static Map<String, Expression> getWhere(String sql)
        throws Exception {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            return getWhere(statement);
        }
        catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static Map<String, Table> getTables(PlainSelect ps) {
        Map<String, Table> result = new HashMap<String, Table>();
        if (ps.getFromItem() instanceof Table) {
            Table t = (Table)ps.getFromItem();
            result.put(t.getName().toUpperCase(), t);
        }
        
        if (ps.getJoins() == null) {
            return result;
        }
        for (Join join : ps.getJoins()) {
            if (join.getRightItem() instanceof Table) {
                Table t = (Table)join.getRightItem();
                result.put(t.getName().toUpperCase(), t);
            }
        }
        return result;
    }
    
    private static Map<String, Table> getTables(List<Table> tables) {
        Map<String, Table> result = new HashMap<String, Table>();
        if (tables == null) {
            return result;
        }
        for (Table table : tables) {
            result.put(table.getName().toUpperCase(), table);
        }
        return result;
    }
    
    private static Map<String, Expression> getInsertColumn(Insert insert)
        throws Exception {
        Map<String, Expression> result = new HashMap<String, Expression>();
        int i = 0;
        List<Expression> expressions = ((ExpressionList)insert.getItemsList()).getExpressions(); //插入的值
        for (Column column : insert.getColumns()) { //插入的列名
            Expression temp = expressions.get(i);
            //if (temp.getClass().getName().endsWith("Value") || temp instanceof JdbcParameter
            // || temp instanceof JdbcNamedParameter) { //插入的值为【值、?、命名参数】时才记录
            if (temp.getClass().getName().endsWith("Value")) { //插入的值为【值、?、命名参数】时才记录
                String name = column.toString();
                //Object value = BeanUtils.getProperty(expressions.get(i), "value");
                result.put(name.toUpperCase(), column);
            }
            ++i;
        }
        return result;
    }
    
    private static Map<String, Expression> getWhere(Expression expression)
        throws Exception {
        
        Map<String, Expression> result = new HashMap<String, Expression>();
        if (expression == null) {
            return result;
        }
        
        Stack<Expression> expreStack = new Stack<Expression>();
        expreStack.push(expression);
        
        while (!expreStack.empty()) {//遍历所有的where表达式
            Expression temp = expreStack.pop();
            if (temp instanceof EqualsTo) { //如果为> < = >=等符号时
            
                Expression left = ((EqualsTo)temp).getLeftExpression();
                Expression right = ((EqualsTo)temp).getRightExpression();
                
                //if (left instanceof Column
                // && (right.getClass().getName().endsWith("Value") || right instanceof JdbcNamedParameter || right instanceof JdbcParameter)) {
                if (left instanceof Column && right.getClass().getName().endsWith("Value")) {
                    String name = left.toString();
                    //Object value = BeanUtils.getProperty(right, "value");
                    result.put(name.toUpperCase(), right);
                }
            }
            else if (temp instanceof OldOracleJoinBinaryExpression) { //跳过 < <= > >=等逻辑处理
                continue;
            }
            else if (temp instanceof BinaryExpression) { //and or等表达式时继续遍历
                expreStack.push(((BinaryExpression)temp).getLeftExpression());
                expreStack.push(((BinaryExpression)temp).getRightExpression());
            }
        }
        
        return result;
    }
}
