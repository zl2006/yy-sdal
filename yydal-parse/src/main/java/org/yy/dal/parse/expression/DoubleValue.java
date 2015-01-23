package org.yy.dal.parse.expression;

/**
 * Every number with a point or a exponential format is a DoubleValue
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class DoubleValue implements Expression {
    
    private double value;
    
    private String stringValue;
    
    public DoubleValue(final String value) {
        String val = value;
        if (val.charAt(0) == '+') {
            val = val.substring(1);
        }
        this.value = Double.parseDouble(val);
        this.stringValue = val;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public double getValue() {
        return value;
    }
    
    public void setValue(double d) {
        value = d;
    }
    
    @Override
    public String toString() {
        return stringValue;
    }
}
