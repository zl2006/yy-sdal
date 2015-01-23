package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.expression.Alias;

/**
 * A lateral subselect followed by an alias.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class LateralSubSelect implements FromItem {
    
    private SubSelect subSelect;
    
    private Alias alias;
    
    private Pivot pivot;
    
    public void setSubSelect(SubSelect subSelect) {
        this.subSelect = subSelect;
    }
    
    public SubSelect getSubSelect() {
        return subSelect;
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
        return pivot;
    }
    
    @Override
    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }
    
    @Override
    public String toString() {
        return "LATERAL" + subSelect.toString() + ((pivot != null) ? " " + pivot : "")
            + ((alias != null) ? alias.toString() : "");
    }
}
