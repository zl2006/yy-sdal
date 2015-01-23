package org.yy.dal.parse.statement.select;

import java.util.Iterator;
import java.util.List;

import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Select implements Statement {
    
    private SelectBody selectBody;
    
    private List<WithItem> withItemsList;
    
    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    public SelectBody getSelectBody() {
        return selectBody;
    }
    
    public void setSelectBody(SelectBody body) {
        selectBody = body;
    }
    
    @Override
    public String toString() {
        StringBuilder retval = new StringBuilder();
        if (withItemsList != null && !withItemsList.isEmpty()) {
            retval.append("WITH ");
            for (Iterator<WithItem> iter = withItemsList.iterator(); iter.hasNext();) {
                WithItem withItem = (WithItem)iter.next();
                retval.append(withItem);
                if (iter.hasNext()) {
                    retval.append(",");
                }
                retval.append(" ");
            }
        }
        retval.append(selectBody);
        return retval.toString();
    }
    
    public List<WithItem> getWithItemsList() {
        return withItemsList;
    }
    
    public void setWithItemsList(List<WithItem> withItemsList) {
        this.withItemsList = withItemsList;
    }
}
