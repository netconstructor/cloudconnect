//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

package ${packageName};

import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;
import org.mule.config.spring.parsers.specific.InvokerMessageProcessorDefinitionParser;
import ${javaClass.getPackage()}.${javaClass.getName()};

public class ${className} extends AbstractPojoNamespaceHandler
{
    public void init()
    {
        <#if hasSetters>
        registerPojo("config", ${javaClass.getName()}.class);
        </#if>
        InvokerMessageProcessorDefinitionParser parser = null;
        <#list operations as operation>
        parser = new InvokerMessageProcessorDefinitionParser("messageProcessor",
            ${className}.class, "${operation.getName()}", new String[] {
             <#list operation.getParameters() as parameter>
                "${parameter.getName()}"<#if parameter_has_next>,</#if>
             </#list>
            } );
        registerMuleBeanDefinitionParser( "<@splitCamelCase>${operation.getName()}</@splitCamelCase>", parser );
        </#list>
    }
}
