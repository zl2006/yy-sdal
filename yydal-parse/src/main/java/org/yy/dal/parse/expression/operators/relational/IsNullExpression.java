package org.yy.dal.parse.expression.operators.relational;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.ExpressionVisitor;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class IsNullExpression implements Expression {
    
    private Expression leftExpression;
    
    private boolean not = false;
    
    public Expression getLeftExpression() {
        return leftExpression;
    }
    
    public boolean isNot() {
        return not;
    }
    
    public void setLeftExpression(Expression expression) {
        leftExpression = expression;
    }
    
    public void setNot(boolean b) {
        not = b;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return leftExpression + " IS " + ((not) ? "NOT " : "") + "NULL";
    }
}
