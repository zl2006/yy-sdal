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
public class WindowRange {
    
    private WindowOffset start;
    
    private WindowOffset end;
    
    public WindowOffset getEnd() {
        return end;
    }
    
    public void setEnd(WindowOffset end) {
        this.end = end;
    }
    
    public WindowOffset getStart() {
        return start;
    }
    
    public void setStart(WindowOffset start) {
        this.start = start;
    }
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(" BETWEEN");
        buffer.append(start);
        buffer.append(" AND");
        buffer.append(end);
        return buffer.toString();
    }
}
