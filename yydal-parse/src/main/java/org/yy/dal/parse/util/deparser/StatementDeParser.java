package org.yy.dal.parse.util.deparser;

import java.util.Iterator;

import org.yy.dal.parse.statement.StatementVisitor;
import org.yy.dal.parse.statement.Statements;
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
import org.yy.dal.parse.statement.select.WithItem;
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
public class StatementDeParser implements StatementVisitor {
    
    private StringBuilder buffer;
    
    public StatementDeParser(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    @Override
    public void visit(CreateIndex createIndex) {
        CreateIndexDeParser createIndexDeParser = new CreateIndexDeParser(buffer);
        createIndexDeParser.deParse(createIndex);
    }
    
    @Override
    public void visit(CreateTable createTable) {
        CreateTableDeParser createTableDeParser = new CreateTableDeParser(buffer);
        createTableDeParser.deParse(createTable);
    }
    
    @Override
    public void visit(CreateView createView) {
        CreateViewDeParser createViewDeParser = new CreateViewDeParser(buffer);
        createViewDeParser.deParse(createView);
    }
    
    @Override
    public void visit(Delete delete) {
        SelectDeParser selectDeParser = new SelectDeParser();
        selectDeParser.setBuffer(buffer);
        ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
        selectDeParser.setExpressionVisitor(expressionDeParser);
        DeleteDeParser deleteDeParser = new DeleteDeParser(expressionDeParser, buffer);
        deleteDeParser.deParse(delete);
    }
    
    @Override
    public void visit(Drop drop) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public void visit(Insert insert) {
        SelectDeParser selectDeParser = new SelectDeParser();
        selectDeParser.setBuffer(buffer);
        ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
        selectDeParser.setExpressionVisitor(expressionDeParser);
        InsertDeParser insertDeParser = new InsertDeParser(expressionDeParser, selectDeParser, buffer);
        insertDeParser.deParse(insert);
        
    }
    
    @Override
    public void visit(Replace replace) {
        SelectDeParser selectDeParser = new SelectDeParser();
        selectDeParser.setBuffer(buffer);
        ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
        selectDeParser.setExpressionVisitor(expressionDeParser);
        ReplaceDeParser replaceDeParser = new ReplaceDeParser(expressionDeParser, selectDeParser, buffer);
        replaceDeParser.deParse(replace);
    }
    
    @Override
    public void visit(Select select) {
        SelectDeParser selectDeParser = new SelectDeParser();
        selectDeParser.setBuffer(buffer);
        ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
        selectDeParser.setExpressionVisitor(expressionDeParser);
        if (select.getWithItemsList() != null && !select.getWithItemsList().isEmpty()) {
            buffer.append("WITH ");
            for (Iterator<WithItem> iter = select.getWithItemsList().iterator(); iter.hasNext();) {
                WithItem withItem = iter.next();
                buffer.append(withItem);
                if (iter.hasNext()) {
                    buffer.append(",");
                }
                buffer.append(" ");
            }
        }
        select.getSelectBody().accept(selectDeParser);
    }
    
    @Override
    public void visit(Truncate truncate) {
    }
    
    @Override
    public void visit(Update update) {
        SelectDeParser selectDeParser = new SelectDeParser();
        selectDeParser.setBuffer(buffer);
        ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
        UpdateDeParser updateDeParser = new UpdateDeParser(expressionDeParser, buffer);
        selectDeParser.setExpressionVisitor(expressionDeParser);
        updateDeParser.deParse(update);
        
    }
    
    public StringBuilder getBuffer() {
        return buffer;
    }
    
    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    @Override
    public void visit(Alter alter) {
        
    }
    
    @Override
    public void visit(Statements stmts) {
        stmts.accept(this);
    }
    
    @Override
    public void visit(Execute execute) {
        SelectDeParser selectDeParser = new SelectDeParser();
        selectDeParser.setBuffer(buffer);
        ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, buffer);
        ExecuteDeParser executeDeParser = new ExecuteDeParser(expressionDeParser, buffer);
        selectDeParser.setExpressionVisitor(expressionDeParser);
        executeDeParser.deParse(execute);
    }
}
