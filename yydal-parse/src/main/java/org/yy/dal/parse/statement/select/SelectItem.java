package org.yy.dal.parse.statement.select;

/**
 *  Anything between "SELECT" and "FROM"<BR>
 * (that is, any column or expression etc to be retrieved with the query)
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public interface SelectItem {
    
    void accept(SelectItemVisitor selectItemVisitor);
}
