package org.yy.dal.parse.expression;

/**
 * A basic class for binary expressions, that is expressions having a left
 * member and a right member which are in turn expressions.
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public abstract class BinaryExpression implements Expression {
    
    private Expression leftExpression;
    
    private Expression rightExpression;
    
    private boolean not = false;
    
    public BinaryExpression() {
    }
    
    public Expression getLeftExpression() {
        return leftExpression;
    }
    
    public Expression getRightExpression() {
        return rightExpression;
    }
    
    public void setLeftExpression(Expression expression) {
        leftExpression = expression;
    }
    
    public void setRightExpression(Expression expression) {
        rightExpression = expression;
    }
    
    public void setNot() {
        not = true;
    }
    
    public boolean isNot() {
        return not;
    }
    
    @Override
    public String toString() {
        return (not ? "NOT " : "") + getLeftExpression() + " " + getStringExpression() + " " + getRightExpression();
    }
    
    public abstract String getStringExpression();
}
