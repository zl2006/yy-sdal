package org.yy.dal.parse.statement.execute;

import org.yy.dal.parse.expression.operators.relational.ExpressionList;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;
import org.yy.dal.parse.statement.select.PlainSelect;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Execute implements Statement {
    
    private String name;
    
    private ExpressionList exprList;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public ExpressionList getExprList() {
        return exprList;
    }
    
    public void setExprList(ExpressionList exprList) {
        this.exprList = exprList;
    }
    
    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return "EXECUTE " + name + " " + PlainSelect.getStringList(exprList.getExpressions(), true, false);
    }
    
}
