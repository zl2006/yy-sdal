package org.yy.dal.parse.expression.operators.relational;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public interface SupportsOldOracleJoinSyntax {
    
    int NO_ORACLE_JOIN = 0;
    
    int ORACLE_JOIN_RIGHT = 1;
    
    int ORACLE_JOIN_LEFT = 2;
    
    int getOldOracleJoinSyntax();
    
    void setOldOracleJoinSyntax(int oldOracleJoinSyntax);
    
    int NO_ORACLE_PRIOR = 0;
    
    int ORACLE_PRIOR_START = 1;
    
    int ORACLE_PRIOR_END = 2;
    
    int getOraclePriorPosition();
    
    void setOraclePriorPosition(int priorPosition);
}
