package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.ExpressionVisitor;
import org.yy.dal.parse.expression.operators.relational.ItemsList;
import org.yy.dal.parse.expression.operators.relational.ItemsListVisitor;

/**
 * A subselect followed by an optional alias.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class SubSelect implements FromItem, Expression, ItemsList {
    
    private SelectBody selectBody;
    
    private Alias alias;
    
    private boolean useBrackets = true;
    
    private Pivot pivot;
    
    @Override
    public void accept(FromItemVisitor fromItemVisitor) {
        fromItemVisitor.visit(this);
    }
    
    public SelectBody getSelectBody() {
        return selectBody;
    }
    
    public void setSelectBody(SelectBody body) {
        selectBody = body;
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
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
        return pivot;
    }
    
    @Override
    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }
    
    public boolean isUseBrackets() {
        return useBrackets;
    }
    
    public void setUseBrackets(boolean useBrackets) {
        this.useBrackets = useBrackets;
    }
    
    @Override
    public void accept(ItemsListVisitor itemsListVisitor) {
        itemsListVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return (useBrackets ? "(" : "") + selectBody + (useBrackets ? ")" : "") + ((pivot != null) ? " " + pivot : "")
            + ((alias != null) ? alias.toString() : "");
    }
}
