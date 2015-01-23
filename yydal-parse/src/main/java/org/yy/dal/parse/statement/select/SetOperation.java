package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.statement.select.SetOperationList.SetOperationType;

/**
 * Single Set-Operation (name). Placeholder for one specific set operation, e.g.
 * union, intersect.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public abstract class SetOperation {
    
    private SetOperationType type;
    
    public SetOperation(SetOperationType type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return type.name();
    }
}
