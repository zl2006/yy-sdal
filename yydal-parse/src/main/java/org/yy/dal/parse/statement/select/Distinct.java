package org.yy.dal.parse.statement.select;

import java.util.List;

/**
 * A DISTINCT [ON (expression, ...)] clause
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Distinct {
    
    private List<SelectItem> onSelectItems;
    
    /**
     * A list of {@link SelectItem}s expressions, as in "select DISTINCT ON
     * (a,b,c) a,b FROM..."
     *
     * @return a list of {@link SelectItem}s expressions
     */
    public List<SelectItem> getOnSelectItems() {
        return onSelectItems;
    }
    
    public void setOnSelectItems(List<SelectItem> list) {
        onSelectItems = list;
    }
    
    @Override
    public String toString() {
        String sql = "DISTINCT";
        
        if (onSelectItems != null && onSelectItems.size() > 0) {
            sql += " ON (" + PlainSelect.getStringList(onSelectItems) + ")";
        }
        
        return sql;
    }
}
