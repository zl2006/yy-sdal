package org.yy.dal.parse.schema;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.statement.select.FromItem;
import org.yy.dal.parse.statement.select.FromItemVisitor;
import org.yy.dal.parse.statement.select.IntoTableVisitor;
import org.yy.dal.parse.statement.select.Pivot;

/**
 * 
 * 数据库表对象, 例如：SELECT columnName FROM [server-name\\server-instance].databaseName.schemaName.tableName
 * 
 * @author  zhouliang
 * @version  [0.1, 2015年1月22日]
 * @since  [yydal-parse/0.1]
 */
public class Table implements FromItem, MultiPartName {
    
    /**
     * 数据库
     */
    private Database database;
    
    /**
     * 模式
     */
    private String schemaName;
    
    /**
     * 表名
     */
    private String name;
    
    /**
     * 别名
     */
    private Alias alias;
    
    private Pivot pivot;
    
    public Table() {
    }
    
    public Table(String name) {
        this.name = name;
    }
    
    public Table(String schemaName, String name) {
        this.schemaName = schemaName;
        this.name = name;
    }
    
    public Table(Database database, String schemaName, String name) {
        this.database = database;
        this.schemaName = schemaName;
        this.name = name;
    }
    
    public Database getDatabase() {
        return database;
    }
    
    public void setDatabase(Database database) {
        this.database = database;
    }
    
    public String getSchemaName() {
        return schemaName;
    }
    
    public void setSchemaName(String string) {
        schemaName = string;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String string) {
        name = string;
    }
    
    @Override
    public Alias getAlias() {
        return alias;
    }
    
    @Override
    public void setAlias(Alias alias) {
        this.alias = alias;
    }
    
    @Override
    public String getFullyQualifiedName() {
        String fqn = "";
        
        if (database != null) {
            fqn += database.getFullyQualifiedName();
        }
        if (!fqn.isEmpty()) {
            fqn += ".";
        }
        
        if (schemaName != null) {
            fqn += schemaName;
        }
        if (!fqn.isEmpty()) {
            fqn += ".";
        }
        
        if (name != null) {
            fqn += name;
        }
        
        return fqn;
    }
    
    @Override
    public void accept(FromItemVisitor fromItemVisitor) {
        fromItemVisitor.visit(this);
    }
    
    public void accept(IntoTableVisitor intoTableVisitor) {
        intoTableVisitor.visit(this);
    }
    
    @Override
    public Pivot getPivot() {
        return pivot;
    }
    
    @Override
    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }
    
    @Override
    public String toString() {
        return getFullyQualifiedName() + ((pivot != null) ? " " + pivot : "")
            + ((alias != null) ? alias.toString() : "");
    }
}
