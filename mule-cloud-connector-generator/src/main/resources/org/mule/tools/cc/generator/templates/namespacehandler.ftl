//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

package ${packageName};

import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;
import org.mule.config.spring.parsers.specific.InvokerMessageProcessorDefinitionParser;
import ${class.getPackage()}.${class.getName()};

public class ${className} extends AbstractPojoNamespaceHandler
{
    public void init()
    {
        <#if class.hasProperties()>
        registerPojo("config", ${javaClass.getName()}.class);
        </#if>
        InvokerMessageProcessorDefinitionParser parser = null;
        <#list class.getOperations() as operation>

        parser = new InvokerMessageProcessorDefinitionParser("messageProcessor",
            ${javaClass.getName()}.class, "${operation.getName()}", new String[] {<#list operation.getParameters() as parameter> "${parameter.getName()}"<#if parameter_has_next>,</#if></#list> });
        registerMuleBeanDefinitionParser("<@splitCamelCase>${operation.getName()}</@splitCamelCase>", parser);
        </#list>
    }
}
