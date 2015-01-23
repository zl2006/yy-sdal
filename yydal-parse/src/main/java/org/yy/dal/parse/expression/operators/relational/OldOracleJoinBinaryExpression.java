package org.yy.dal.parse.expression.operators.relational;

import org.yy.dal.parse.expression.BinaryExpression;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public abstract class OldOracleJoinBinaryExpression extends BinaryExpression implements SupportsOldOracleJoinSyntax {
    
    private int oldOracleJoinSyntax = NO_ORACLE_JOIN;
    
    private int oraclePriorPosition = NO_ORACLE_PRIOR;
    
    @Override
    public void setOldOracleJoinSyntax(int oldOracleJoinSyntax) {
        this.oldOracleJoinSyntax = oldOracleJoinSyntax;
        if (oldOracleJoinSyntax < 0 || oldOracleJoinSyntax > 2) {
            throw new IllegalArgumentException("unknown join type for oracle found (type=" + oldOracleJoinSyntax + ")");
        }
    }
    
    @Override
    public String toString() {
        return (isNot() ? "NOT " : "") + (oraclePriorPosition == ORACLE_PRIOR_START ? "PRIOR " : "")
            + getLeftExpression() + (oldOracleJoinSyntax == ORACLE_JOIN_RIGHT ? "(+)" : "") + " "
            + getStringExpression() + " " + (oraclePriorPosition == ORACLE_PRIOR_END ? "PRIOR " : "")
            + getRightExpression() + (oldOracleJoinSyntax == ORACLE_JOIN_LEFT ? "(+)" : "");
    }
    
    @Override
    public int getOldOracleJoinSyntax() {
        return oldOracleJoinSyntax;
    }
    
    @Override
    public int getOraclePriorPosition() {
        return oraclePriorPosition;
    }
    
    @Override
    public void setOraclePriorPosition(int oraclePriorPosition) {
        this.oraclePriorPosition = oraclePriorPosition;
    }
}
