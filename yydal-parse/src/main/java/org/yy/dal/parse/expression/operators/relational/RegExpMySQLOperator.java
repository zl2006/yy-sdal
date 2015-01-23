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
public class RegExpMySQLOperator extends BinaryExpression {
    
    private RegExpMatchOperatorType operatorType;
    
    public RegExpMySQLOperator(RegExpMatchOperatorType operatorType) {
        if (operatorType == null) {
            throw new NullPointerException();
        }
        this.operatorType = operatorType;
    }
    
    public RegExpMatchOperatorType getOperatorType() {
        return operatorType;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    @Override
    public String getStringExpression() {
        switch (operatorType) {
            case MATCH_CASESENSITIVE:
                return "REGEXP BINARY";
            case MATCH_CASEINSENSITIVE:
                return "REGEXP";
            default:
        }
        return null;
    }
}
