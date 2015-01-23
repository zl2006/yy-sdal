package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.expression.Expression;

/**
 * An element (column reference) in an "ORDER BY" clause.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class OrderByElement {
    
    public enum NullOrdering {
        
        NULLS_FIRST, NULLS_LAST
    }
    
    private Expression expression;
    
    private boolean asc = true;
    
    private NullOrdering nullOrdering;
    
    private boolean ascDesc = false;
    
    public boolean isAsc() {
        return asc;
    }
    
    public NullOrdering getNullOrdering() {
        return nullOrdering;
    }
    
    public void setNullOrdering(NullOrdering nullOrdering) {
        this.nullOrdering = nullOrdering;
    }
    
    public void setAsc(boolean b) {
        asc = b;
    }
    
    public void setAscDescPresent(boolean b) {
        ascDesc = b;
    }
    
    public boolean isAscDescPresent() {
        return ascDesc;
    }
    
    public void accept(OrderByVisitor orderByVisitor) {
        orderByVisitor.visit(this);
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(expression.toString());
        
        if (!asc) {
            b.append(" DESC");
        }
        else if (ascDesc) {
            b.append(" ASC");
        }
        
        if (nullOrdering != null) {
            b.append(' ');
            b.append(nullOrdering == NullOrdering.NULLS_FIRST ? "NULLS FIRST" : "NULLS LAST");
        }
        return b.toString();
    }
}
