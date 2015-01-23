package org.yy.dal.parse.util.deparser;

import org.yy.dal.parse.expression.ExpressionVisitor;
import org.yy.dal.parse.statement.delete.Delete;

/**
 * A class to de-parse (that is, tranform from JSqlParser hierarchy into a
 * string) 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class DeleteDeParser {
    
    private StringBuilder buffer;
    
    private ExpressionVisitor expressionVisitor;
    
    /**
     * @param expressionVisitor a {@link ExpressionVisitor} to de-parse
     * expressions. It has to share the same<br>
     * StringBuilder (buffer parameter) as this object in order to work
     * @param buffer the buffer that will be filled with the select
     */
    public DeleteDeParser(ExpressionVisitor expressionVisitor, StringBuilder buffer) {
        this.buffer = buffer;
        this.expressionVisitor = expressionVisitor;
    }
    
    public StringBuilder getBuffer() {
        return buffer;
    }
    
    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    public void deParse(Delete delete) {
        buffer.append("DELETE FROM ").append(delete.getTable().getFullyQualifiedName());
        if (delete.getWhere() != null) {
            buffer.append(" WHERE ");
            delete.getWhere().accept(expressionVisitor);
        }
        
    }
    
    public ExpressionVisitor getExpressionVisitor() {
        return expressionVisitor;
    }
    
    public void setExpressionVisitor(ExpressionVisitor visitor) {
        expressionVisitor = visitor;
    }
}
