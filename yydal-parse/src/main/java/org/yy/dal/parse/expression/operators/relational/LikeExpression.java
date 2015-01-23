package org.yy.dal.parse.expression.operators.relational;

import org.yy.dal.parse.expression.BinaryExpression;
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
public class LikeExpression extends BinaryExpression {
    
    private boolean not = false;
    
    private String escape = null;
    
    @Override
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
    
    @Override
    public String getStringExpression() {
        return ((not) ? "NOT " : "") + "LIKE";
    }
    
    @Override
    public String toString() {
        String retval = super.toString();
        if (escape != null) {
            retval += " ESCAPE " + "'" + escape + "'";
        }
        
        return retval;
    }
    
    public String getEscape() {
        return escape;
    }
    
    public void setEscape(String escape) {
        this.escape = escape;
    }
}
