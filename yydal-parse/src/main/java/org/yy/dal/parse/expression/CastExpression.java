package org.yy.dal.parse.expression;

import org.yy.dal.parse.statement.create.table.ColDataType;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class CastExpression implements Expression {
    
    private Expression leftExpression;
    
    private ColDataType type;
    
    private boolean useCastKeyword = true;
    
    public ColDataType getType() {
        return type;
    }
    
    public void setType(ColDataType type) {
        this.type = type;
    }
    
    public Expression getLeftExpression() {
        return leftExpression;
    }
    
    public void setLeftExpression(Expression expression) {
        leftExpression = expression;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public boolean isUseCastKeyword() {
        return useCastKeyword;
    }
    
    public void setUseCastKeyword(boolean useCastKeyword) {
        this.useCastKeyword = useCastKeyword;
    }
    
    @Override
    public String toString() {
        if (useCastKeyword) {
            return "CAST(" + leftExpression + " AS " + type.toString() + ")";
        }
        else {
            return leftExpression + "::" + type.toString();
        }
    }
}
