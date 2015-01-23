package org.yy.dal.parse.expression;

/**
 *  It represents a "-" or "+" before an expression
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class SignedExpression implements Expression {
    
    private char sign;
    
    private Expression expression;
    
    public SignedExpression(char sign, Expression expression) {
        setSign(sign);
        setExpression(expression);
    }
    
    public char getSign() {
        return sign;
    }
    
    public final void setSign(char sign) {
        this.sign = sign;
        if (sign != '+' && sign != '-') {
            throw new IllegalArgumentException("illegal sign character, only + - allowed");
        }
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public final void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return getSign() + expression.toString();
    }
}
