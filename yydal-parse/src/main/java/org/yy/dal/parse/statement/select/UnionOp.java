package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.statement.select.SetOperationList.SetOperationType;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class UnionOp extends SetOperation {
    
    private boolean distinct;
    
    private boolean all;
    
    public UnionOp() {
        super(SetOperationType.UNION);
    }
    
    public boolean isAll() {
        return all;
    }
    
    public void setAll(boolean all) {
        this.all = all;
    }
    
    public boolean isDistinct() {
        return distinct;
    }
    
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
    
    @Override
    public String toString() {
        String allDistinct = "";
        if (isAll()) {
            allDistinct = " ALL";
        }
        else if (isDistinct()) {
            allDistinct = " DISTINCT";
        }
        return super.toString() + allDistinct;
    }
}
