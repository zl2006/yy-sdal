package org.yy.dal.parse.statement.select;

import java.util.List;

/**
 * One of the parts of a "WITH" clause of a "SELECT" statement
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class WithItem implements SelectBody {
    
    private String name;
    
    private List<SelectItem> withItemList;
    
    private SelectBody selectBody;
    
    /**
     * The name of this WITH item (for example, "myWITH" in "WITH myWITH AS
     * (SELECT A,B,C))"
     *
     * @return the name of this WITH
     */
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * The {@link SelectBody} of this WITH item is the part after the "AS"
     * keyword
     *
     * @return {@link SelectBody} of this WITH item
     */
    public SelectBody getSelectBody() {
        return selectBody;
    }
    
    public void setSelectBody(SelectBody selectBody) {
        this.selectBody = selectBody;
    }
    
    /**
     * The {@link SelectItem}s in this WITH (for example the A,B,C in "WITH
     * mywith (A,B,C) AS ...")
     *
     * @return a list of {@link SelectItem}s
     */
    public List<SelectItem> getWithItemList() {
        return withItemList;
    }
    
    public void setWithItemList(List<SelectItem> withItemList) {
        this.withItemList = withItemList;
    }
    
    @Override
    public String toString() {
        return name + ((withItemList != null) ? " " + PlainSelect.getStringList(withItemList, true, true) : "")
            + " AS (" + selectBody + ")";
    }
    
    @Override
    public void accept(SelectVisitor visitor) {
        visitor.visit(this);
    }
}
