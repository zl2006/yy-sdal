/*
* 文 件 名:  DbTable.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  分表
* 修 改 人:  zhouliang
* 修改时间:  2016年2月16日
* 修改内容:  <修改内容>
*/
package org.yy.dal.nm;

/**
* 描述分表信息,表名_[分表数量]:分表规则名称(规则明细)
* 例如：user_[6]:hash(user_id)
* 实际表名为user_0   user_2 ... user_5
* 
* 如果是单表（采用表分区时）可以定义为user_[1]:hash(user_id)
* 实际表名为user,不在有后辍
* 
* @author  zhouliang
* @version  [1.0, 2016年2月16日]
* @since  [yy-sdal/1.0]
*/
public class DbTable {
    
    /**
     * 表名
     */
    private String tableName;
    
    /**
     * 每个实例中此表的分表数
     */
    private int tableNum;
    
    /**
     * 分表规则，由分表算法（明细），例如：hash(user_id)
     */
    private String ruleDesc;
    
    /**
     * 规则名称
     */
    private String ruleName;
    
    public DbTable(String tableName, int tableNum, String ruleDesc) {
        this.tableName = tableName;
        this.tableNum = tableNum;
        this.ruleDesc = ruleDesc;
        if (ruleDesc != null && ruleDesc.indexOf("(") > 0) {
            this.ruleName = ruleDesc.substring(0, ruleDesc.indexOf("("));
        }
    }
    
    /**
    * @return 返回 tableName
    */
    public String getTableName() {
        return tableName;
    }
    
    /**
    * @param 对tableName进行赋值
    */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
    * @return 返回 tableNum
    */
    public int getTableNum() {
        return tableNum;
    }
    
    /**
    * @param 对tableNum进行赋值
    */
    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
    
    /**
    * @return 返回 ruleDesc
    */
    public String getRuleDesc() {
        return ruleDesc;
    }
    
    /**
    * @return 返回 name
    */
    public String getRuleName() {
        return ruleName;
    }
    
    /**
    * @param 对ruleDesc进行赋值
    */
    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "DbTable [tableName=" + tableName + ", tableNum=" + tableNum + ", ruleDesc=" + ruleDesc + "]";
    }
    
}
