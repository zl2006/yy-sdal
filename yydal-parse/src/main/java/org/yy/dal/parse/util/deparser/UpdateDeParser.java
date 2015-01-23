package org.yy.dal.parse.util.deparser;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.ExpressionVisitor;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.statement.select.Join;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.update.Update;

/**
 *  A class to de-parse (that is, tranform from JSqlParser hierarchy into a
 * string) 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class UpdateDeParser {
    
    private StringBuilder buffer;
    
    private ExpressionVisitor expressionVisitor;
    
    /**
     * @param expressionVisitor a {@link ExpressionVisitor} to de-parse
     * expressions. It has to share the same<br>
     * StringBuilder (buffer parameter) as this object in order to work
     * @param buffer the buffer that will be filled with the select
     */
    public UpdateDeParser(ExpressionVisitor expressionVisitor, StringBuilder buffer) {
        this.buffer = buffer;
        this.expressionVisitor = expressionVisitor;
    }
    
    public StringBuilder getBuffer() {
        return buffer;
    }
    
    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    public void deParse(Update update) {
        buffer.append("UPDATE ").append(PlainSelect.getStringList(update.getTables(), true, false)).append(" SET ");
        for (int i = 0; i < update.getColumns().size(); i++) {
            Column column = update.getColumns().get(i);
            buffer.append(column.getFullyQualifiedName()).append(" = ");
            
            Expression expression = update.getExpressions().get(i);
            expression.accept(expressionVisitor);
            if (i < update.getColumns().size() - 1) {
                buffer.append(", ");
            }
        }
        
        if (update.getFromItem() != null) {
            buffer.append(" FROM ").append(update.getFromItem());
            if (update.getJoins() != null) {
                for (Join join : update.getJoins()) {
                    if (join.isSimple()) {
                        buffer.append(", ").append(join);
                    }
                    else {
                        buffer.append(" ").append(join);
                    }
                }
            }
        }
        
        if (update.getWhere() != null) {
            buffer.append(" WHERE ");
            update.getWhere().accept(expressionVisitor);
        }
        
    }
    
    public ExpressionVisitor getExpressionVisitor() {
        return expressionVisitor;
    }
    
    public void setExpressionVisitor(ExpressionVisitor visitor) {
        expressionVisitor = visitor;
    }
}
