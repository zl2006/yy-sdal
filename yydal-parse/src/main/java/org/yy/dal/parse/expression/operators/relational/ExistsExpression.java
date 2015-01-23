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
public class ExistsExpression implements Expression {
    
    private Expression rightExpression;
    
    private boolean not = false;
    
    public Expression getRightExpression() {
        return rightExpression;
    }
    
    public void setRightExpression(Expression expression) {
        rightExpression = expression;
    }
    
    public boolean isNot() {
        return not;
    }
    
    public void setNot(boolean b) {
        not = b;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public String getStringExpression() {
        return ((not) ? "NOT " : "") + "EXISTS";
    }
    
    @Override
    public String toString() {
        return getStringExpression() + " " + rightExpression.toString();
    }
}
