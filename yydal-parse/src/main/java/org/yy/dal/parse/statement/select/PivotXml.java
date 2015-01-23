package org.yy.dal.parse.statement.select;

import java.util.List;

import org.yy.dal.parse.schema.Column;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class PivotXml extends Pivot {
    
    private SelectBody inSelect;
    
    private boolean inAny = false;
    
    @Override
    public void accept(PivotVisitor pivotVisitor) {
        pivotVisitor.visit(this);
    }
    
    public SelectBody getInSelect() {
        return inSelect;
    }
    
    public void setInSelect(SelectBody inSelect) {
        this.inSelect = inSelect;
    }
    
    public boolean isInAny() {
        return inAny;
    }
    
    public void setInAny(boolean inAny) {
        this.inAny = inAny;
    }
    
    @Override
    public String toString() {
        List<Column> forColumns = getForColumns();
        String in = inAny ? "ANY" : inSelect == null ? PlainSelect.getStringList(getInItems()) : inSelect.toString();
        return "PIVOT XML (" + PlainSelect.getStringList(getFunctionItems()) + " FOR "
            + PlainSelect.getStringList(forColumns, true, forColumns != null && forColumns.size() > 1) + " IN (" + in
            + "))";
    }
    
}
