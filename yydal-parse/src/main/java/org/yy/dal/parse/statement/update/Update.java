package org.yy.dal.parse.statement.update;

import java.util.List;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;
import org.yy.dal.parse.statement.select.FromItem;
import org.yy.dal.parse.statement.select.Join;
import org.yy.dal.parse.statement.select.PlainSelect;

/**
 * The update statement.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Update implements Statement {
    
    private List<Table> tables;
    
    private Expression where;
    
    private List<Column> columns;
    
    private List<Expression> expressions;
    
    private FromItem fromItem;
    
    private List<Join> joins;
    
    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    public List<Table> getTables() {
        return tables;
    }
    
    public Expression getWhere() {
        return where;
    }
    
    public void setTables(List<Table> list) {
        tables = list;
    }
    
    public void setWhere(Expression expression) {
        where = expression;
    }
    
    /**
     * The {@link net.sf.jsqlparser.schema.Column}s in this update (as col1 and
     * col2 in UPDATE col1='a', col2='b')
     *
     * @return a list of {@link net.sf.jsqlparser.schema.Column}s
     */
    public List<Column> getColumns() {
        return columns;
    }
    
    /**
     * The {@link Expression}s in this update (as 'a' and 'b' in UPDATE
     * col1='a', col2='b')
     *
     * @return a list of {@link Expression}s
     */
    public List<Expression> getExpressions() {
        return expressions;
    }
    
    public void setColumns(List<Column> list) {
        columns = list;
    }
    
    public void setExpressions(List<Expression> list) {
        expressions = list;
    }
    
    public FromItem getFromItem() {
        return fromItem;
    }
    
    public void setFromItem(FromItem fromItem) {
        this.fromItem = fromItem;
    }
    
    public List<Join> getJoins() {
        return joins;
    }
    
    public void setJoins(List<Join> joins) {
        this.joins = joins;
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("UPDATE ");
        b.append(PlainSelect.getStringList(getTables(), true, false)).append(" SET ");
        for (int i = 0; i < getColumns().size(); i++) {
            if (i != 0) {
                b.append(", ");
            }
            b.append(columns.get(i)).append(" = ");
            b.append(expressions.get(i));
        }
        
        if (fromItem != null) {
            b.append(" FROM ").append(fromItem);
            if (joins != null) {
                for (Join join : joins) {
                    if (join.isSimple()) {
                        b.append(", ").append(join);
                    }
                    else {
                        b.append(" ").append(join);
                    }
                }
            }
        }
        
        if (where != null) {
            b.append(" WHERE ");
            b.append(where);
        }
        return b.toString();
    }
}
