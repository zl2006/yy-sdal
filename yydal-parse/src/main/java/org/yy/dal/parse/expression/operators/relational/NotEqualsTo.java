
package org.yy.dal.parse.expression.operators.relational;

import org.yy.dal.parse.expression.ExpressionVisitor;

/**
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class NotEqualsTo extends OldOracleJoinBinaryExpression {

    private final String operator;

    public NotEqualsTo() {
        operator = "<>";
    }

    public NotEqualsTo(String operator) {
        this.operator = operator;
        if (!"!=".equals(operator) && !"<>".equals(operator)) {
            throw new IllegalArgumentException("only <> or != allowed");
        }
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String getStringExpression() {
        return operator;
    }
}
