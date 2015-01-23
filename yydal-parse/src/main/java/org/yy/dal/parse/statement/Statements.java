package org.yy.dal.parse.statement;

import java.util.List;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Statements {
    
    private List<Statement> statements;
    
    public List<Statement> getStatements() {
        return statements;
    }
    
    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
    
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Statement stmt : statements) {
            b.append(stmt.toString()).append(";\n");
        }
        return b.toString();
    }
}
