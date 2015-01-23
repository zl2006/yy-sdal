package org.yy.dal.parse.expression;

/**
 *  It represents an expression like "(" expression ")"
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Parenthesis implements Expression {
    
    private Expression expression;
    
    private boolean not = false;
    
    public Parenthesis() {
    }
    
    public Parenthesis(Expression expression) {
        setExpression(expression);
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public final void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public void setNot() {
        not = true;
    }
    
    public boolean isNot() {
        return not;
    }
    
    @Override
    public String toString() {
        return (not ? "NOT " : "") + "(" + expression + ")";
    }
}
