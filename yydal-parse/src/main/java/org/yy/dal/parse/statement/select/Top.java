package org.yy.dal.parse.statement.select;

/**
 * A top clause in the form [TOP (row_count) or TOP row_count]
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Top {
    
    private long rowCount;
    
    private boolean rowCountJdbcParameter = false;
    
    private boolean hasParenthesis = false;
    
    private boolean isPercentage = false;
    
    public long getRowCount() {
        return rowCount;
    }
    
    // TODO instead of a plain number, an expression should be added, which could be a NumberExpression, a GroupedExpression or a JdbcParameter
    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }
    
    public boolean isRowCountJdbcParameter() {
        return rowCountJdbcParameter;
    }
    
    public void setRowCountJdbcParameter(boolean rowCountJdbcParameter) {
        this.rowCountJdbcParameter = rowCountJdbcParameter;
    }
    
    public boolean hasParenthesis() {
        return hasParenthesis;
    }
    
    public void setParenthesis(boolean hasParenthesis) {
        this.hasParenthesis = hasParenthesis;
    }
    
    public boolean isPercentage() {
        return isPercentage;
    }
    
    public void setPercentage(boolean percentage) {
        this.isPercentage = percentage;
    }
    
    @Override
    public String toString() {
        String result = "TOP ";
        
        if (hasParenthesis) {
            result += "(";
        }
        
        result += rowCountJdbcParameter ? "?" : rowCount;
        
        if (hasParenthesis) {
            result += ")";
        }
        
        if (isPercentage) {
            result += " PERCENT";
        }
        
        return result;
    }
}
