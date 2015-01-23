package org.yy.dal.parse.util;

import java.util.LinkedList;
import java.util.List;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.expression.BinaryExpression;
import org.yy.dal.parse.statement.select.AllColumns;
import org.yy.dal.parse.statement.select.AllTableColumns;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.SelectExpressionItem;
import org.yy.dal.parse.statement.select.SelectItem;
import org.yy.dal.parse.statement.select.SelectItemVisitor;
import org.yy.dal.parse.statement.select.SelectVisitor;
import org.yy.dal.parse.statement.select.SetOperationList;
import org.yy.dal.parse.statement.select.WithItem;

/**
 * Connect all selected expressions with a binary expression. Out of select a,b
 * from table one gets select a || b as expr from table. The type of binary
 * expression is set by overwriting this class abstract method
 * createBinaryExpression.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public abstract class ConnectExpressionsVisitor implements SelectVisitor, SelectItemVisitor {
    
    private String alias = "expr";
    
    private final List<SelectExpressionItem> itemsExpr = new LinkedList<SelectExpressionItem>();
    
    public ConnectExpressionsVisitor() {
    }
    
    public ConnectExpressionsVisitor(String alias) {
        this.alias = alias;
    }
    
    /**
     * Create instances of this binary expression that connects all selected
     * expressions.
     *
     * @return
     */
    protected abstract BinaryExpression createBinaryExpression();
    
    @Override
    public void visit(PlainSelect plainSelect) {
        for (SelectItem item : plainSelect.getSelectItems()) {
            item.accept(this);
        }
        
        if (itemsExpr.size() > 1) {
            BinaryExpression binExpr = createBinaryExpression();
            binExpr.setLeftExpression(itemsExpr.get(0).getExpression());
            for (int i = 1; i < itemsExpr.size() - 1; i++) {
                binExpr.setRightExpression(itemsExpr.get(i).getExpression());
                BinaryExpression binExpr2 = createBinaryExpression();
                binExpr2.setLeftExpression(binExpr);
                binExpr = binExpr2;
            }
            binExpr.setRightExpression(itemsExpr.get(itemsExpr.size() - 1).getExpression());
            
            SelectExpressionItem sei = new SelectExpressionItem();
            sei.setExpression(binExpr);
            
            plainSelect.getSelectItems().clear();
            plainSelect.getSelectItems().add(sei);
        }
        
        ((SelectExpressionItem)plainSelect.getSelectItems().get(0)).setAlias(new Alias(alias));
    }
    
    @Override
    public void visit(SetOperationList setOpList) {
        for (PlainSelect select : setOpList.getPlainSelects()) {
            select.accept(this);
        }
    }
    
    @Override
    public void visit(WithItem withItem) {
    }
    
    @Override
    public void visit(AllTableColumns allTableColumns) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void visit(AllColumns allColumns) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        itemsExpr.add(selectExpressionItem);
    }
}
