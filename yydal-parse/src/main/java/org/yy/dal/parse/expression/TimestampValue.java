package org.yy.dal.parse.expression;

import java.sql.Timestamp;

/**
 * A Timestamp in the form {ts 'yyyy-mm-dd hh:mm:ss.f . . .'}
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class TimestampValue implements Expression {
    
    private Timestamp value;
    
    public TimestampValue(String value) {
        this.value = Timestamp.valueOf(value.substring(1, value.length() - 1));
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public Timestamp getValue() {
        return value;
    }
    
    public void setValue(Timestamp d) {
        value = d;
    }
    
    @Override
    public String toString() {
        return "{ts '" + value + "'}";
    }
}
