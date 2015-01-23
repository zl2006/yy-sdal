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
public class ExceptOp extends SetOperation {
    
    public ExceptOp() {
        super(SetOperationType.EXCEPT);
    }
}
