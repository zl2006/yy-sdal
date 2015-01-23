package org.yy.dal.parse.util.deparser;

import org.yy.dal.parse.expression.ExpressionVisitor;
import org.yy.dal.parse.statement.execute.Execute;
import org.yy.dal.parse.statement.select.PlainSelect;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class ExecuteDeParser {
    
    private StringBuilder buffer;
    
    private ExpressionVisitor expressionVisitor;
    
    /**
     * @param expressionVisitor a {@link ExpressionVisitor} to de-parse
     * expressions. It has to share the same<br>
     * StringBuilder (buffer parameter) as this object in order to work
     * @param buffer the buffer that will be filled with the select
     */
    public ExecuteDeParser(ExpressionVisitor expressionVisitor, StringBuilder buffer) {
        this.buffer = buffer;
        this.expressionVisitor = expressionVisitor;
    }
    
    public StringBuilder getBuffer() {
        return buffer;
    }
    
    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    public void deParse(Execute execute) {
        buffer.append("EXECUTE ").append(execute.getName());
        buffer.append(" ").append(PlainSelect.getStringList(execute.getExprList().getExpressions(), true, false));
    }
    
    public ExpressionVisitor getExpressionVisitor() {
        return expressionVisitor;
    }
    
    public void setExpressionVisitor(ExpressionVisitor visitor) {
        expressionVisitor = visitor;
    }
}
