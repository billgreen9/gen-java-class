package ${packageName};

import java.util.List;

/**
 *  @author 
 */
public class ${className} {
    <#list attrs as attr>
    //${attr.comment} 
    private ${attr.type} ${attr.field};
    </#list>

    <#list attrs as attr>
    public void set${attr.name?cap_first}(${attr.type} ${attr.field}){
        this.${attr.field} = ${attr.field};
    }
    public ${attr.type} get${attr.name?cap_first}(){
        return this.${attr.field};
    }

    </#list>
}