package org.yy.dal.parse.expression;

import java.util.List;

import org.yy.dal.parse.expression.operators.relational.ExpressionList;
import org.yy.dal.parse.statement.select.OrderByElement;
import org.yy.dal.parse.statement.select.PlainSelect;

/**
 * 
Analytic function. The name of the function is variable but the parameters
 * following the special analytic function path. e.g. row_number() over (order
 * by test). Additional there can be an expression for an analytical aggregate
 * like sum(col) or the "all collumns" wildcard like count(*).
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class AnalyticExpression implements Expression {
    
    //private List<Column> partitionByColumns;
    private ExpressionList partitionExpressionList;
    
    private List<OrderByElement> orderByElements;
    
    private String name;
    
    private Expression expression;
    
    private Expression offset;
    
    private Expression defaultValue;
    
    private boolean allColumns = false;
    
    private WindowElement windowElement;
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public List<OrderByElement> getOrderByElements() {
        return orderByElements;
    }
    
    public void setOrderByElements(List<OrderByElement> orderByElements) {
        this.orderByElements = orderByElements;
    }
    
    //	public List<Column> getPartitionByColumns() {
    //		return partitionByColumns;
    //	}
    //
    //	public void setPartitionByColumns(List<Column> partitionByColumns) {
    //		this.partitionByColumns = partitionByColumns;
    //	}
    
    public ExpressionList getPartitionExpressionList() {
        return partitionExpressionList;
    }
    
    public void setPartitionExpressionList(ExpressionList partitionExpressionList) {
        this.partitionExpressionList = partitionExpressionList;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    public Expression getOffset() {
        return offset;
    }
    
    public void setOffset(Expression offset) {
        this.offset = offset;
    }
    
    public Expression getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(Expression defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public WindowElement getWindowElement() {
        return windowElement;
    }
    
    public void setWindowElement(WindowElement windowElement) {
        this.windowElement = windowElement;
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        
        b.append(name).append("(");
        if (expression != null) {
            b.append(expression.toString());
            if (offset != null) {
                b.append(", ").append(offset.toString());
                if (defaultValue != null) {
                    b.append(", ").append(defaultValue.toString());
                }
            }
        }
        else if (isAllColumns()) {
            b.append("*");
        }
        b.append(") OVER (");
        
        toStringPartitionBy(b);
        toStringOrderByElements(b);
        
        b.append(")");
        
        return b.toString();
    }
    
    public boolean isAllColumns() {
        return allColumns;
    }
    
    public void setAllColumns(boolean allColumns) {
        this.allColumns = allColumns;
    }
    
    private void toStringPartitionBy(StringBuilder b) {
        if (partitionExpressionList != null && !partitionExpressionList.getExpressions().isEmpty()) {
            b.append("PARTITION BY ");
            b.append(PlainSelect.getStringList(partitionExpressionList.getExpressions(), true, false));
            b.append(" ");
        }
    }
    
    private void toStringOrderByElements(StringBuilder b) {
        if (orderByElements != null && !orderByElements.isEmpty()) {
            b.append("ORDER BY ");
            for (int i = 0; i < orderByElements.size(); i++) {
                if (i > 0) {
                    b.append(", ");
                }
                b.append(orderByElements.get(i).toString());
            }
            
            if (windowElement != null) {
                b.append(' ');
                b.append(windowElement);
            }
        }
    }
}
