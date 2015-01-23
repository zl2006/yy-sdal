package org.yy.dal.parse.statement.select;

/**
 *  A limit clause in the form [LIMIT {[offset,] row_count) | (row_count | ALL)
 * OFFSET offset}]
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Limit {
    
    private long offset;
    
    private long rowCount;
    
    private boolean rowCountJdbcParameter = false;
    
    private boolean offsetJdbcParameter = false;
    
    private boolean limitAll;
    
    private boolean limitNull = false;
    
    public long getOffset() {
        return offset;
    }
    
    public long getRowCount() {
        return rowCount;
    }
    
    public void setOffset(long l) {
        offset = l;
    }
    
    public void setRowCount(long l) {
        rowCount = l;
    }
    
    public boolean isOffsetJdbcParameter() {
        return offsetJdbcParameter;
    }
    
    public boolean isRowCountJdbcParameter() {
        return rowCountJdbcParameter;
    }
    
    public void setOffsetJdbcParameter(boolean b) {
        offsetJdbcParameter = b;
    }
    
    public void setRowCountJdbcParameter(boolean b) {
        rowCountJdbcParameter = b;
    }
    
    /**
     * @return true if the limit is "LIMIT ALL [OFFSET ...])
     */
    public boolean isLimitAll() {
        return limitAll;
    }
    
    public void setLimitAll(boolean b) {
        limitAll = b;
    }
    
    /**
     * @return true if the limit is "LIMIT NULL [OFFSET ...])
     */
    public boolean isLimitNull() {
        return limitNull;
    }
    
    public void setLimitNull(boolean b) {
        limitNull = b;
    }
    
    @Override
    public String toString() {
        String retVal = "";
        if (limitNull) {
            retVal += " LIMIT NULL";
        }
        else if (rowCount >= 0 || rowCountJdbcParameter) {
            retVal += " LIMIT " + (rowCountJdbcParameter ? "?" : rowCount + "");
        }
        if (offset > 0 || offsetJdbcParameter) {
            retVal += " OFFSET " + (offsetJdbcParameter ? "?" : offset + "");
        }
        return retVal;
    }
}
