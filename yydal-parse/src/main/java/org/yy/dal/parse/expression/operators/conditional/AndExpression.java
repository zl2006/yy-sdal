package org.yy.dal.parse.expression.operators.conditional;

import org.yy.dal.parse.expression.BinaryExpression;
import org.yy.dal.parse.expression.Expression;
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
public class AndExpression extends BinaryExpression {
    
    public AndExpression(Expression leftExpression, Expression rightExpression) {
        setLeftExpression(leftExpression);
        setRightExpression(rightExpression);
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    @Override
    public String getStringExpression() {
        return "AND";
    }
}
