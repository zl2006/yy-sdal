package org.yy.dal.parse.util.deparser;

import java.util.Iterator;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.ExpressionVisitor;
import org.yy.dal.parse.expression.operators.relational.ExpressionList;
import org.yy.dal.parse.expression.operators.relational.ItemsListVisitor;
import org.yy.dal.parse.expression.operators.relational.MultiExpressionList;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.statement.replace.Replace;
import org.yy.dal.parse.statement.select.SelectVisitor;
import org.yy.dal.parse.statement.select.SubSelect;

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
public class ReplaceDeParser implements ItemsListVisitor {
    
    private StringBuilder buffer;
    
    private ExpressionVisitor expressionVisitor;
    
    private SelectVisitor selectVisitor;
    
    public ReplaceDeParser() {
    }
    
    /**
     * @param expressionVisitor a {@link ExpressionVisitor} to de-parse
     * expressions. It has to share the same<br>
     * StringBuilder (buffer parameter) as this object in order to work
     * @param selectVisitor a {@link SelectVisitor} to de-parse
     * {@link net.sf.jsqlparser.statement.select.Select}s. It has to share the
     * same<br>
     * StringBuilder (buffer parameter) as this object in order to work
     * @param buffer the buffer that will be filled with the select
     */
    public ReplaceDeParser(ExpressionVisitor expressionVisitor, SelectVisitor selectVisitor, StringBuilder buffer) {
        this.buffer = buffer;
        this.expressionVisitor = expressionVisitor;
        this.selectVisitor = selectVisitor;
    }
    
    public StringBuilder getBuffer() {
        return buffer;
    }
    
    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    public void deParse(Replace replace) {
        buffer.append("REPLACE ").append(replace.getTable().getFullyQualifiedName());
        if (replace.getItemsList() != null) {
            if (replace.getColumns() != null) {
                buffer.append(" (");
                for (int i = 0; i < replace.getColumns().size(); i++) {
                    Column column = replace.getColumns().get(i);
                    buffer.append(column.getFullyQualifiedName());
                    if (i < replace.getColumns().size() - 1) {
                        buffer.append(", ");
                    }
                }
                buffer.append(") ");
            }
            else {
                buffer.append(" ");
            }
            
        }
        else {
            buffer.append(" SET ");
            for (int i = 0; i < replace.getColumns().size(); i++) {
                Column column = replace.getColumns().get(i);
                buffer.append(column.getFullyQualifiedName()).append("=");
                
                Expression expression = replace.getExpressions().get(i);
                expression.accept(expressionVisitor);
                if (i < replace.getColumns().size() - 1) {
                    buffer.append(", ");
                }
                
            }
        }
        
        if (replace.getItemsList() != null) {
            // REPLACE mytab SELECT * FROM mytab2
            // or VALUES ('as', ?, 565)
            
            if (replace.isUseValues()) {
                buffer.append(" VALUES");
            }
            
            buffer.append(replace.getItemsList());
        }
    }
    
    @Override
    public void visit(ExpressionList expressionList) {
        buffer.append(" VALUES (");
        for (Iterator<Expression> iter = expressionList.getExpressions().iterator(); iter.hasNext();) {
            Expression expression = iter.next();
            expression.accept(expressionVisitor);
            if (iter.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append(")");
    }
    
    @Override
    public void visit(SubSelect subSelect) {
        subSelect.getSelectBody().accept(selectVisitor);
    }
    
    public ExpressionVisitor getExpressionVisitor() {
        return expressionVisitor;
    }
    
    public SelectVisitor getSelectVisitor() {
        return selectVisitor;
    }
    
    public void setExpressionVisitor(ExpressionVisitor visitor) {
        expressionVisitor = visitor;
    }
    
    public void setSelectVisitor(SelectVisitor visitor) {
        selectVisitor = visitor;
    }
    
    @Override
    public void visit(MultiExpressionList multiExprList) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
