package org.yy.dal.parse.expression;

import java.sql.Date;

/**
 * A Date in the form {d 'yyyy-mm-dd'}
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class DateValue implements Expression {
    
    private Date value;
    
    public DateValue(String value) {
        this.value = Date.valueOf(value.substring(1, value.length() - 1));
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public Date getValue() {
        return value;
    }
    
    public void setValue(Date d) {
        value = d;
    }
}
