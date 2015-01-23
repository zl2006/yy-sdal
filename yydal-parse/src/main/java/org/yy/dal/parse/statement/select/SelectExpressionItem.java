package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.expression.Expression;

/**
 *  An expression as in "SELECT expr1 AS EXPR"
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class SelectExpressionItem implements SelectItem {
    
    private Expression expression;
    
    private Alias alias;
    
    public SelectExpressionItem() {
    }
    
    public SelectExpressionItem(Expression expression) {
        this.expression = expression;
    }
    
    public Alias getAlias() {
        return alias;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setAlias(Alias alias) {
        this.alias = alias;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public void accept(SelectItemVisitor selectItemVisitor) {
        selectItemVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return expression + ((alias != null) ? alias.toString() : "");
    }
}
