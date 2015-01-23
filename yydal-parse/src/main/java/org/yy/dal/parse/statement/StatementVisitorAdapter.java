package org.yy.dal.parse.statement;

import org.yy.dal.parse.statement.alter.Alter;
import org.yy.dal.parse.statement.create.index.CreateIndex;
import org.yy.dal.parse.statement.create.table.CreateTable;
import org.yy.dal.parse.statement.create.view.CreateView;
import org.yy.dal.parse.statement.delete.Delete;
import org.yy.dal.parse.statement.drop.Drop;
import org.yy.dal.parse.statement.execute.Execute;
import org.yy.dal.parse.statement.insert.Insert;
import org.yy.dal.parse.statement.replace.Replace;
import org.yy.dal.parse.statement.select.Select;
import org.yy.dal.parse.statement.truncate.Truncate;
import org.yy.dal.parse.statement.update.Update;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class StatementVisitorAdapter implements StatementVisitor {
    @Override
    public void visit(Select select) {
        
    }
    
    @Override
    public void visit(Delete delete) {
        
    }
    
    @Override
    public void visit(Update update) {
        
    }
    
    @Override
    public void visit(Insert insert) {
        
    }
    
    @Override
    public void visit(Replace replace) {
        
    }
    
    @Override
    public void visit(Drop drop) {
        
    }
    
    @Override
    public void visit(Truncate truncate) {
        
    }
    
    @Override
    public void visit(CreateIndex createIndex) {
        
    }
    
    @Override
    public void visit(CreateTable createTable) {
        
    }
    
    @Override
    public void visit(CreateView createView) {
        
    }
    
    @Override
    public void visit(Alter alter) {
        
    }
    
    @Override
    public void visit(Statements stmts) {
        
    }
    
    @Override
    public void visit(Execute execute) {
        
    }
}
