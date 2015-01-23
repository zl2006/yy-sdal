package org.yy.dal.parse.expression;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class WindowOffset {
    
    public enum Type {
        
        PRECEDING, FOLLOWING, CURRENT, EXPR
    }
    
    private Expression expression;
    
    private Type type;
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        if (expression != null) {
            buffer.append(' ').append(expression);
            if (type != null) {
                buffer.append(' ');
                buffer.append(type);
            }
        }
        else {
            switch (type) {
                case PRECEDING:
                    buffer.append(" UNBOUNDED PRECEDING");
                    break;
                case FOLLOWING:
                    buffer.append(" UNBOUNDED FOLLOWING");
                    break;
                case CURRENT:
                    buffer.append(" CURRENT ROW");
                    break;
                default:
                    break;
            }
        }
        return buffer.toString();
    }
}
