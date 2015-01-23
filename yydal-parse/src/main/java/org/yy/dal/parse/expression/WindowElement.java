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
public class WindowElement {
    
    public enum Type {
        
        ROWS, RANGE
    }
    
    private Type type;
    
    private WindowOffset offset;
    
    private WindowRange range;
    
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    public WindowOffset getOffset() {
        return offset;
    }
    
    public void setOffset(WindowOffset offset) {
        this.offset = offset;
    }
    
    public WindowRange getRange() {
        return range;
    }
    
    public void setRange(WindowRange range) {
        this.range = range;
    }
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(type.toString());
        
        if (offset != null) {
            buffer.append(offset.toString());
        }
        else if (range != null) {
            buffer.append(range.toString());
        }
        
        return buffer.toString();
    }
}
