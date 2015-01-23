package org.yy.dal.parse.expression;

/**
 * Every number without a point or an exponential format is a LongValue
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class LongValue implements Expression {
    
    private long value;
    
    private String stringValue;
    
    public LongValue(final String value) {
        String val = value;
        if (val.charAt(0) == '+') {
            val = val.substring(1);
        }
        this.value = Long.parseLong(val);
        this.stringValue = val;
    }
    
    public LongValue(long value) {
        this.value = value;
        stringValue = String.valueOf(value);
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public long getValue() {
        return value;
    }
    
    public void setValue(long d) {
        value = d;
    }
    
    public String getStringValue() {
        return stringValue;
    }
    
    public void setStringValue(String string) {
        stringValue = string;
    }
    
    @Override
    public String toString() {
        return getStringValue();
    }
}
