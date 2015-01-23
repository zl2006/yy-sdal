package org.yy.dal.parse.expression.operators.relational;

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
public class ItemsListVisitorAdapter implements ItemsListVisitor {
    @Override
    public void visit(SubSelect subSelect) {
        
    }
    
    @Override
    public void visit(ExpressionList expressionList) {
        
    }
    
    @Override
    public void visit(MultiExpressionList multiExprList) {
        for (ExpressionList list : multiExprList.getExprList()) {
            visit(list);
        }
    }
}
