package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.expression.Alias;

/**
 * A table created by "(tab1 join tab2)".
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class SubJoin implements FromItem {
    
    private FromItem left;
    
    private Join join;
    
    private Alias alias;
    
    private Pivot pivot;
    
    @Override
    public void accept(FromItemVisitor fromItemVisitor) {
        fromItemVisitor.visit(this);
    }
    
    public FromItem getLeft() {
        return left;
    }
    
    public void setLeft(FromItem l) {
        left = l;
    }
    
    public Join getJoin() {
        return join;
    }
    
    public void setJoin(Join j) {
        join = j;
    }
    
    @Override
    public Pivot getPivot() {
        return pivot;
    }
    
    @Override
    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
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
    public String toString() {
        return "(" + left + " " + join + ")" + ((pivot != null) ? " " + pivot : "")
            + ((alias != null) ? alias.toString() : "");
    }
}
