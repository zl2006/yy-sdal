package org.yy.dal.parse.expression.operators.relational;

import java.util.List;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.statement.select.PlainSelect;

/**
 * A list of expressions, as in SELECT A FROM TAB WHERE B IN (expr1,expr2,expr3)
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class ExpressionList implements ItemsList {
    
    private List<Expression> expressions;
    
    public ExpressionList() {
    }
    
    public ExpressionList(List<Expression> expressions) {
        this.expressions = expressions;
    }
    
    public List<Expression> getExpressions() {
        return expressions;
    }
    
    public void setExpressions(List<Expression> list) {
        expressions = list;
    }
    
    @Override
    public void accept(ItemsListVisitor itemsListVisitor) {
        itemsListVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return PlainSelect.getStringList(expressions, true, true);
    }
}
