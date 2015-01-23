package org.yy.dal.parse.expression;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class OracleHierarchicalExpression implements Expression {
    
    private Expression startExpression;
    
    private Expression connectExpression;
    
    private boolean noCycle = false;
    
    public Expression getStartExpression() {
        return startExpression;
    }
    
    public void setStartExpression(Expression startExpression) {
        this.startExpression = startExpression;
    }
    
    public Expression getConnectExpression() {
        return connectExpression;
    }
    
    public void setConnectExpression(Expression connectExpression) {
        this.connectExpression = connectExpression;
    }
    
    public boolean isNoCycle() {
        return noCycle;
    }
    
    public void setNoCycle(boolean noCycle) {
        this.noCycle = noCycle;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (startExpression != null) {
            b.append(" START WITH ").append(startExpression.toString());
        }
        b.append(" CONNECT BY ");
        if (isNoCycle()) {
            b.append("NOCYCLE ");
        }
        b.append(connectExpression.toString());
        return b.toString();
    }
}
