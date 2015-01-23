package org.yy.dal.parse.expression;

import org.yy.dal.parse.statement.select.SubSelect;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class AnyComparisonExpression implements Expression {
    
    private SubSelect subSelect;
    
    public AnyComparisonExpression(SubSelect subSelect) {
        this.subSelect = subSelect;
    }
    
    public SubSelect getSubSelect() {
        return subSelect;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
