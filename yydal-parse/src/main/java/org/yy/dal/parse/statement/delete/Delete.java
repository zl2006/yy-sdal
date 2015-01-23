package org.yy.dal.parse.statement.delete;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Delete implements Statement {
    
    private Table table;
    
    private Expression where;
    
    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    public Table getTable() {
        return table;
    }
    
    public Expression getWhere() {
        return where;
    }
    
    public void setTable(Table name) {
        table = name;
    }
    
    public void setWhere(Expression expression) {
        where = expression;
    }
    
    @Override
    public String toString() {
        return "DELETE FROM " + table + ((where != null) ? " WHERE " + where : "");
    }
}
