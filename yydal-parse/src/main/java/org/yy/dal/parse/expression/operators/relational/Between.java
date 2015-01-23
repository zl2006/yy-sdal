package org.yy.dal.parse.expression.operators.relational;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.ExpressionVisitor;

/**
 * 
 * A "BETWEEN" expr1 expr2 statement
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Between implements Expression {
    
    private Expression leftExpression;
    
    private boolean not = false;
    
    private Expression betweenExpressionStart;
    
    private Expression betweenExpressionEnd;
    
    public Expression getBetweenExpressionEnd() {
        return betweenExpressionEnd;
    }
    
    public Expression getBetweenExpressionStart() {
        return betweenExpressionStart;
    }
    
    public Expression getLeftExpression() {
        return leftExpression;
    }
    
    public boolean isNot() {
        return not;
    }
    
    public void setBetweenExpressionEnd(Expression expression) {
        betweenExpressionEnd = expression;
    }
    
    public void setBetweenExpressionStart(Expression expression) {
        betweenExpressionStart = expression;
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
        return leftExpression + " " + (not ? "NOT " : "") + "BETWEEN " + betweenExpressionStart + " AND "
            + betweenExpressionEnd;
    }
}
