package org.yy.dal.parse.statement.select;

/**
 * All the columns (as in "SELECT * FROM ...")
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class AllColumns implements SelectItem {
    
    public AllColumns() {
    }
    
    @Override
    public void accept(SelectItemVisitor selectItemVisitor) {
        selectItemVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return "*";
    }
}
