package org.yy.dal.parse.statement.select;

import java.util.Iterator;
import java.util.List;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.expression.operators.relational.ExpressionList;
import org.yy.dal.parse.expression.operators.relational.MultiExpressionList;

/**
 *  This is a container for a values item within a select statement. It holds
 * some syntactical stuff that differs from values within an insert statement.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class ValuesList implements FromItem {
    
    private Alias alias;
    
    private MultiExpressionList multiExpressionList;
    
    private boolean noBrackets = false;
    
    private List<String> columnNames;
    
    public ValuesList() {
    }
    
    public ValuesList(MultiExpressionList multiExpressionList) {
        this.multiExpressionList = multiExpressionList;
    }
    
    @Override
    public void accept(FromItemVisitor fromItemVisitor) {
        fromItemVisitor.visit(this);
    }
    
    @Override
    public Alias getAlias() {
        return alias;
    }
    
    @Override
    public void setAlias(Alias alias) {
        this.alias = alias;
    }
    
    @Override
    public Pivot getPivot() {
        return null;
    }
    
    @Override
    public void setPivot(Pivot pivot) {
    }
    
    public MultiExpressionList getMultiExpressionList() {
        return multiExpressionList;
    }
    
    public void setMultiExpressionList(MultiExpressionList multiExpressionList) {
        this.multiExpressionList = multiExpressionList;
    }
    
    public boolean isNoBrackets() {
        return noBrackets;
    }
    
    public void setNoBrackets(boolean noBrackets) {
        this.noBrackets = noBrackets;
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        
        b.append("(VALUES ");
        for (Iterator<ExpressionList> it = getMultiExpressionList().getExprList().iterator(); it.hasNext();) {
            b.append(PlainSelect.getStringList(it.next().getExpressions(), true, !isNoBrackets()));
            if (it.hasNext()) {
                b.append(", ");
            }
        }
        b.append(")");
        if (alias != null) {
            b.append(alias.toString());
            
            if (columnNames != null) {
                b.append("(");
                for (Iterator<String> it = columnNames.iterator(); it.hasNext();) {
                    b.append(it.next());
                    if (it.hasNext()) {
                        b.append(", ");
                    }
                }
                b.append(")");
            }
        }
        return b.toString();
    }
    
    public List<String> getColumnNames() {
        return columnNames;
    }
    
    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
}
