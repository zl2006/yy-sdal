package org.yy.dal.parse.expression;

/**
 * 别名
 * 
 * @author  zhouliang
 * @version  [0.1, 2015年1月22日]
 * @since  [yydal-parse/0.1]
 */
public class Alias {
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 是否需要as关键字
     */
    private boolean useAs = true;
    
    public Alias(String name) {
        this.name = name;
    }
    
    public Alias(String name, boolean useAs) {
        this.name = name;
        this.useAs = useAs;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isUseAs() {
        return useAs;
    }
    
    public void setUseAs(boolean useAs) {
        this.useAs = useAs;
    }
    
    @Override
    public String toString() {
        return (useAs ? " AS " : " ") + name;
    }
}
