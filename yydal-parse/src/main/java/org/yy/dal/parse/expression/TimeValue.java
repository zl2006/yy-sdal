package org.yy.dal.parse.expression;

import java.sql.Time;

/**
 * A Time in the form {t 'hh:mm:ss'}
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class TimeValue implements Expression {
    
    private Time value;
    
    public TimeValue(String value) {
        this.value = Time.valueOf(value.substring(1, value.length() - 1));
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public Time getValue() {
        return value;
    }
    
    public void setValue(Time d) {
        value = d;
    }
    
    @Override
    public String toString() {
        return "{t '" + value + "'}";
    }
}
