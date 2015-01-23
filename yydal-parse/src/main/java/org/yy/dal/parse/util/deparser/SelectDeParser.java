package org.yy.dal.parse.util.deparser;

import java.util.Iterator;
import java.util.List;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.ExpressionVisitor;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.select.AllColumns;
import org.yy.dal.parse.statement.select.AllTableColumns;
import org.yy.dal.parse.statement.select.FromItem;
import org.yy.dal.parse.statement.select.FromItemVisitor;
import org.yy.dal.parse.statement.select.Join;
import org.yy.dal.parse.statement.select.LateralSubSelect;
import org.yy.dal.parse.statement.select.Limit;
import org.yy.dal.parse.statement.select.OrderByElement;
import org.yy.dal.parse.statement.select.OrderByVisitor;
import org.yy.dal.parse.statement.select.Pivot;
import org.yy.dal.parse.statement.select.PivotVisitor;
import org.yy.dal.parse.statement.select.PivotXml;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.SelectExpressionItem;
import org.yy.dal.parse.statement.select.SelectItem;
import org.yy.dal.parse.statement.select.SelectItemVisitor;
import org.yy.dal.parse.statement.select.SelectVisitor;
import org.yy.dal.parse.statement.select.SetOperationList;
import org.yy.dal.parse.statement.select.SubJoin;
import org.yy.dal.parse.statement.select.SubSelect;
import org.yy.dal.parse.statement.select.Top;
import org.yy.dal.parse.statement.select.ValuesList;
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
public class SelectDeParser implements SelectVisitor, OrderByVisitor, SelectItemVisitor, FromItemVisitor, PivotVisitor {
    
    private StringBuilder buffer;
    
    private ExpressionVisitor expressionVisitor;
    
    public SelectDeParser() {
    }
    
    /**
     * @param expressionVisitor a {@link ExpressionVisitor} to de-parse
     * expressions. It has to share the same<br>
     * StringBuilder (buffer parameter) as this object in order to work
     * @param buffer the buffer that will be filled with the select
     */
    public SelectDeParser(ExpressionVisitor expressionVisitor, StringBuilder buffer) {
        this.buffer = buffer;
        this.expressionVisitor = expressionVisitor;
    }
    
    @Override
    public void visit(PlainSelect plainSelect) {
        buffer.append("SELECT ");
        Top top = plainSelect.getTop();
        if (top != null) {
            buffer.append(top).append(" ");
        }
        if (plainSelect.getDistinct() != null) {
            buffer.append("DISTINCT ");
            if (plainSelect.getDistinct().getOnSelectItems() != null) {
                buffer.append("ON (");
                for (Iterator<SelectItem> iter = plainSelect.getDistinct().getOnSelectItems().iterator(); iter.hasNext();) {
                    SelectItem selectItem = iter.next();
                    selectItem.accept(this);
                    if (iter.hasNext()) {
                        buffer.append(", ");
                    }
                }
                buffer.append(") ");
            }
            
        }
        
        for (Iterator<SelectItem> iter = plainSelect.getSelectItems().iterator(); iter.hasNext();) {
            SelectItem selectItem = iter.next();
            selectItem.accept(this);
            if (iter.hasNext()) {
                buffer.append(", ");
            }
        }
        
        if (plainSelect.getIntoTables() != null) {
            buffer.append(" INTO ");
            for (Iterator<Table> iter = plainSelect.getIntoTables().iterator(); iter.hasNext();) {
                visit(iter.next());
                if (iter.hasNext()) {
                    buffer.append(", ");
                }
            }
        }
        
        if (plainSelect.getFromItem() != null) {
            buffer.append(" FROM ");
            plainSelect.getFromItem().accept(this);
        }
        
        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                deparseJoin(join);
            }
        }
        
        if (plainSelect.getWhere() != null) {
            buffer.append(" WHERE ");
            plainSelect.getWhere().accept(expressionVisitor);
        }
        
        if (plainSelect.getOracleHierarchical() != null) {
            plainSelect.getOracleHierarchical().accept(expressionVisitor);
        }
        
        if (plainSelect.getGroupByColumnReferences() != null) {
            buffer.append(" GROUP BY ");
            for (Iterator<Expression> iter = plainSelect.getGroupByColumnReferences().iterator(); iter.hasNext();) {
                Expression columnReference = iter.next();
                columnReference.accept(expressionVisitor);
                if (iter.hasNext()) {
                    buffer.append(", ");
                }
            }
        }
        
        if (plainSelect.getHaving() != null) {
            buffer.append(" HAVING ");
            plainSelect.getHaving().accept(expressionVisitor);
        }
        
        if (plainSelect.getOrderByElements() != null) {
            deparseOrderBy(plainSelect.isOracleSiblings(), plainSelect.getOrderByElements());
        }
        
        if (plainSelect.getLimit() != null) {
            deparseLimit(plainSelect.getLimit());
        }
        
    }
    
    @Override
    public void visit(OrderByElement orderBy) {
        orderBy.getExpression().accept(expressionVisitor);
        if (!orderBy.isAsc()) {
            buffer.append(" DESC");
        }
        else if (orderBy.isAscDescPresent()) {
            buffer.append(" ASC");
        }
        if (orderBy.getNullOrdering() != null) {
            buffer.append(' ');
            buffer.append(orderBy.getNullOrdering() == OrderByElement.NullOrdering.NULLS_FIRST ? "NULLS FIRST"
                : "NULLS LAST");
        }
    }
    
    public void visit(Column column) {
        buffer.append(column.getFullyQualifiedName());
    }
    
    @Override
    public void visit(AllTableColumns allTableColumns) {
        buffer.append(allTableColumns.getTable().getFullyQualifiedName()).append(".*");
    }
    
    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        selectExpressionItem.getExpression().accept(expressionVisitor);
        if (selectExpressionItem.getAlias() != null) {
            buffer.append(selectExpressionItem.getAlias().toString());
        }
        
    }
    
    @Override
    public void visit(SubSelect subSelect) {
        buffer.append("(");
        subSelect.getSelectBody().accept(this);
        buffer.append(")");
        Pivot pivot = subSelect.getPivot();
        if (pivot != null) {
            pivot.accept(this);
        }
        Alias alias = subSelect.getAlias();
        if (alias != null) {
            buffer.append(alias.toString());
        }
    }
    
    @Override
    public void visit(Table tableName) {
        buffer.append(tableName.getFullyQualifiedName());
        Pivot pivot = tableName.getPivot();
        if (pivot != null) {
            pivot.accept(this);
        }
        Alias alias = tableName.getAlias();
        if (alias != null) {
            buffer.append(alias);
        }
    }
    
    @Override
    public void visit(Pivot pivot) {
        List<Column> forColumns = pivot.getForColumns();
        buffer.append(" PIVOT (")
            .append(PlainSelect.getStringList(pivot.getFunctionItems()))
            .append(" FOR ")
            .append(PlainSelect.getStringList(forColumns, true, forColumns != null && forColumns.size() > 1))
            .append(" IN ")
            .append(PlainSelect.getStringList(pivot.getInItems(), true, true))
            .append(")");
    }
    
    @Override
    public void visit(PivotXml pivot) {
        List<Column> forColumns = pivot.getForColumns();
        buffer.append(" PIVOT XML (")
            .append(PlainSelect.getStringList(pivot.getFunctionItems()))
            .append(" FOR ")
            .append(PlainSelect.getStringList(forColumns, true, forColumns != null && forColumns.size() > 1))
            .append(" IN (");
        if (pivot.isInAny()) {
            buffer.append("ANY");
        }
        else if (pivot.getInSelect() != null) {
            buffer.append(pivot.getInSelect());
        }
        else {
            buffer.append(PlainSelect.getStringList(pivot.getInItems()));
        }
        buffer.append("))");
    }
    
    public void deparseOrderBy(List<OrderByElement> orderByElements) {
        deparseOrderBy(false, orderByElements);
    }
    
    public void deparseOrderBy(boolean oracleSiblings, List<OrderByElement> orderByElements) {
        if (oracleSiblings) {
            buffer.append(" ORDER SIBLINGS BY ");
        }
        else {
            buffer.append(" ORDER BY ");
        }
        for (Iterator<OrderByElement> iter = orderByElements.iterator(); iter.hasNext();) {
            OrderByElement orderByElement = iter.next();
            orderByElement.accept(this);
            if (iter.hasNext()) {
                buffer.append(", ");
            }
        }
    }
    
    public void deparseLimit(Limit limit) {
        // LIMIT n OFFSET skip
        if (limit.isRowCountJdbcParameter()) {
            buffer.append(" LIMIT ");
            buffer.append("?");
        }
        else if (limit.getRowCount() >= 0) {
            buffer.append(" LIMIT ");
            buffer.append(limit.getRowCount());
        }
        else if (limit.isLimitNull()) {
            buffer.append(" LIMIT NULL");
        }
        
        if (limit.isOffsetJdbcParameter()) {
            buffer.append(" OFFSET ?");
        }
        else if (limit.getOffset() != 0) {
            buffer.append(" OFFSET ").append(limit.getOffset());
        }
        
    }
    
    public StringBuilder getBuffer() {
        return buffer;
    }
    
    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    public ExpressionVisitor getExpressionVisitor() {
        return expressionVisitor;
    }
    
    public void setExpressionVisitor(ExpressionVisitor visitor) {
        expressionVisitor = visitor;
    }
    
    @Override
    public void visit(SubJoin subjoin) {
        buffer.append("(");
        subjoin.getLeft().accept(this);
        deparseJoin(subjoin.getJoin());
        buffer.append(")");
        
        if (subjoin.getPivot() != null) {
            subjoin.getPivot().accept(this);
        }
    }
    
    public void deparseJoin(Join join) {
        if (join.isSimple()) {
            buffer.append(", ");
        }
        else {
            
            if (join.isRight()) {
                buffer.append(" RIGHT");
            }
            else if (join.isNatural()) {
                buffer.append(" NATURAL");
            }
            else if (join.isFull()) {
                buffer.append(" FULL");
            }
            else if (join.isLeft()) {
                buffer.append(" LEFT");
            }
            else if (join.isCross()) {
                buffer.append(" CROSS");
            }
            
            if (join.isOuter()) {
                buffer.append(" OUTER");
            }
            else if (join.isInner()) {
                buffer.append(" INNER");
            }
            
            buffer.append(" JOIN ");
            
        }
        
        FromItem fromItem = join.getRightItem();
        fromItem.accept(this);
        if (join.getOnExpression() != null) {
            buffer.append(" ON ");
            join.getOnExpression().accept(expressionVisitor);
        }
        if (join.getUsingColumns() != null) {
            buffer.append(" USING (");
            for (Iterator<Column> iterator = join.getUsingColumns().iterator(); iterator.hasNext();) {
                Column column = iterator.next();
                buffer.append(column.getFullyQualifiedName());
                if (iterator.hasNext()) {
                    buffer.append(", ");
                }
            }
            buffer.append(")");
        }
        
    }
    
    @Override
    public void visit(SetOperationList list) {
        for (int i = 0; i < list.getPlainSelects().size(); i++) {
            if (i != 0) {
                buffer.append(' ').append(list.getOperations().get(i - 1)).append(' ');
            }
            buffer.append("(");
            PlainSelect plainSelect = list.getPlainSelects().get(i);
            plainSelect.accept(this);
            buffer.append(")");
        }
        if (list.getOrderByElements() != null) {
            deparseOrderBy(list.getOrderByElements());
        }
        
        if (list.getLimit() != null) {
            deparseLimit(list.getLimit());
        }
    }
    
    @Override
    public void visit(WithItem withItem) {
        buffer.append(withItem.toString());
    }
    
    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        buffer.append(lateralSubSelect.toString());
    }
    
    @Override
    public void visit(ValuesList valuesList) {
        buffer.append(valuesList.toString());
    }
    
    @Override
    public void visit(AllColumns allColumns) {
        buffer.append('*');
    }
}
