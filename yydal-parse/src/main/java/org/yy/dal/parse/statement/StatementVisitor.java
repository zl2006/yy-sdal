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
public interface StatementVisitor {
    
    void visit(Select select);
    
    void visit(Delete delete);
    
    void visit(Update update);
    
    void visit(Insert insert);
    
    void visit(Replace replace);
    
    void visit(Drop drop);
    
    void visit(Truncate truncate);
    
    void visit(CreateIndex createIndex);
    
    void visit(CreateTable createTable);
    
    void visit(CreateView createView);
    
    void visit(Alter alter);
    
    void visit(Statements stmts);
    
    void visit(Execute execute);
}
