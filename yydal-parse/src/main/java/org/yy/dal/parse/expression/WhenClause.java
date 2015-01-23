package org.yy.dal.parse.expression;

/**
 *  A clause of following syntax: WHEN condition THEN expression. Which is part
 * of a CaseExpression.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class WhenClause implements Expression {
    
    private Expression whenExpression;
    
    private Expression thenExpression;
    
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.jsqlparser.expression.Expression#accept(net.sf.jsqlparser.expression.ExpressionVisitor)
     */
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    /**
     * @return Returns the thenExpression.
     */
    public Expression getThenExpression() {
        return thenExpression;
    }
    
    /**
     * @param thenExpression The thenExpression to set.
     */
    public void setThenExpression(Expression thenExpression) {
        this.thenExpression = thenExpression;
    }
    
    /**
     * @return Returns the whenExpression.
     */
    public Expression getWhenExpression() {
        return whenExpression;
    }
    
    /**
     * @param whenExpression The whenExpression to set.
     */
    public void setWhenExpression(Expression whenExpression) {
        this.whenExpression = whenExpression;
    }
    
    @Override
    public String toString() {
        return "WHEN " + whenExpression + " THEN " + thenExpression;
    }
}
