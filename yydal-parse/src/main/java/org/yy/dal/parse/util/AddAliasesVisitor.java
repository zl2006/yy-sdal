package org.yy.dal.parse.util;

import java.util.LinkedList;
import java.util.List;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.statement.select.AllColumns;
import org.yy.dal.parse.statement.select.AllTableColumns;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.SelectExpressionItem;
import org.yy.dal.parse.statement.select.SelectItem;
import org.yy.dal.parse.statement.select.SelectItemVisitor;
import org.yy.dal.parse.statement.select.SelectVisitor;
import org.yy.dal.parse.statement.select.SetOperationList;
import org.yy.dal.parse.statement.select.WithItem;

/**
 * Add aliases to every column and expression selected by a select - statement.
 * Existing aliases are recognized and preserved. This class standard uses a
 * prefix of A and a counter to generate new aliases (e.g. A1, A5, ...). This
 * behaviour can be altered.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class AddAliasesVisitor implements SelectVisitor, SelectItemVisitor {
    
    private List<String> aliases = new LinkedList<String>();
    
    private boolean firstRun = true;
    
    private int counter = 0;
    
    private String prefix = "A";
    
    @Override
    public void visit(PlainSelect plainSelect) {
        firstRun = true;
        counter = 0;
        aliases.clear();
        for (SelectItem item : plainSelect.getSelectItems()) {
            item.accept(this);
        }
        firstRun = false;
        for (SelectItem item : plainSelect.getSelectItems()) {
            item.accept(this);
        }
    }
    
    @Override
    public void visit(SetOperationList setOpList) {
        for (PlainSelect select : setOpList.getPlainSelects()) {
            select.accept(this);
        }
    }
    
    @Override
    public void visit(AllTableColumns allTableColumns) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        if (firstRun) {
            if (selectExpressionItem.getAlias() != null) {
                aliases.add(selectExpressionItem.getAlias().getName().toUpperCase());
            }
        }
        else {
            if (selectExpressionItem.getAlias() == null) {
                
                while (true) {
                    String alias = getNextAlias().toUpperCase();
                    if (!aliases.contains(alias)) {
                        aliases.add(alias);
                        selectExpressionItem.setAlias(new Alias(alias));
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Calculate next alias name to use.
     *
     * @return
     */
    protected String getNextAlias() {
        counter++;
        return prefix + counter;
    }
    
    /**
     * Set alias prefix.
     *
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    @Override
    public void visit(WithItem withItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void visit(AllColumns allColumns) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
