package org.yy.dal.parse.util.deparser;

import java.util.Iterator;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.ExpressionVisitor;
import org.yy.dal.parse.expression.operators.relational.ExpressionList;
import org.yy.dal.parse.expression.operators.relational.ItemsListVisitor;
import org.yy.dal.parse.expression.operators.relational.MultiExpressionList;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.statement.insert.Insert;
import org.yy.dal.parse.statement.select.SelectExpressionItem;
import org.yy.dal.parse.statement.select.SelectVisitor;
import org.yy.dal.parse.statement.select.SubSelect;
import org.yy.dal.parse.statement.select.WithItem;

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
public class InsertDeParser implements ItemsListVisitor {
    
    private StringBuilder buffer;
    
    private ExpressionVisitor expressionVisitor;
    
    private SelectVisitor selectVisitor;
    
    public InsertDeParser() {
    }
    
    /**
     * @param expressionVisitor a {@link ExpressionVisitor} to de-parse
     * {@link net.sf.jsqlparser.expression.Expression}s. It has to share the
     * same<br>
     * StringBuilder (buffer parameter) as this object in order to work
     * @param selectVisitor a {@link SelectVisitor} to de-parse
     * {@link net.sf.jsqlparser.statement.select.Select}s. It has to share the
     * same<br>
     * StringBuilder (buffer parameter) as this object in order to work
     * @param buffer the buffer that will be filled with the insert
     */
    public InsertDeParser(ExpressionVisitor expressionVisitor, SelectVisitor selectVisitor, StringBuilder buffer) {
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
    
    public void deParse(Insert insert) {
        buffer.append("INSERT INTO ");
        buffer.append(insert.getTable().getFullyQualifiedName());
        if (insert.getColumns() != null) {
            buffer.append(" (");
            for (Iterator<Column> iter = insert.getColumns().iterator(); iter.hasNext();) {
                Column column = iter.next();
                buffer.append(column.getColumnName());
                if (iter.hasNext()) {
                    buffer.append(", ");
                }
            }
            buffer.append(")");
        }
        
        if (insert.getItemsList() != null) {
            insert.getItemsList().accept(this);
        }
        
        if (insert.getSelect() != null) {
            buffer.append(" ");
            if (insert.isUseSelectBrackets()) {
                buffer.append("(");
            }
            if (insert.getSelect().getWithItemsList() != null) {
                buffer.append("WITH ");
                for (WithItem with : insert.getSelect().getWithItemsList()) {
                    with.accept(selectVisitor);
                }
                buffer.append(" ");
            }
            insert.getSelect().getSelectBody().accept(selectVisitor);
            if (insert.isUseSelectBrackets()) {
                buffer.append(")");
            }
        }
        
        if (insert.isReturningAllColumns()) {
            buffer.append(" RETURNING *");
        }
        else if (insert.getReturningExpressionList() != null) {
            buffer.append(" RETURNING ");
            for (Iterator<SelectExpressionItem> iter = insert.getReturningExpressionList().iterator(); iter.hasNext();) {
                buffer.append(iter.next().toString());
                if (iter.hasNext()) {
                    buffer.append(", ");
                }
            }
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
    public void visit(MultiExpressionList multiExprList) {
        buffer.append(" VALUES ");
        for (Iterator<ExpressionList> it = multiExprList.getExprList().iterator(); it.hasNext();) {
            buffer.append("(");
            for (Iterator<Expression> iter = it.next().getExpressions().iterator(); iter.hasNext();) {
                Expression expression = iter.next();
                expression.accept(expressionVisitor);
                if (iter.hasNext()) {
                    buffer.append(", ");
                }
            }
            buffer.append(")");
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
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
}
