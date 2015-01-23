package org.yy.dal.parse.expression;

/**
 * A string as in 'example_string'
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class StringValue implements Expression {
    
    private String value = "";
    
    public StringValue(String escapedValue) {
        // romoving "'" at the start and at the end
        value = escapedValue.substring(1, escapedValue.length() - 1);
    }
    
    public String getValue() {
        return value;
    }
    
    public String getNotExcapedValue() {
        StringBuilder buffer = new StringBuilder(value);
        int index = 0;
        int deletesNum = 0;
        while ((index = value.indexOf("''", index)) != -1) {
            buffer.deleteCharAt(index - deletesNum);
            index += 2;
            deletesNum++;
        }
        return buffer.toString();
    }
    
    public void setValue(String string) {
        value = string;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return "'" + value + "'";
    }
}
