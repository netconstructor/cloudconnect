//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

package ${packageName};

import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;
import org.mule.config.spring.parsers.specific.InvokerMessageProcessorDefinitionParser;
<#if factoryBean?has_content>
import org.mule.config.spring.parsers.generic.OrphanDefinitionParser;
</#if>
import ${class.getPackage()}.${class.getName()};

public class ${className} extends AbstractPojoNamespaceHandler
{
    public void init()
    {
        <#if class.hasProperties() && !factoryBean?has_content>
        registerPojo("config", ${class.getName()}.class);
        </#if>
        <#if factoryBean?has_content>
        registerMuleBeanDefinitionParser("config",
            new OrphanDefinitionParser(${factoryBean}.class, true)).addIgnored("name");
        </#if>
        InvokerMessageProcessorDefinitionParser parser = null;
        <#list class.getOperations() as operation>

        parser = new InvokerMessageProcessorDefinitionParser("messageProcessor",
            ${class.getName()}.class, "${operation.getName()}", new String[] {<#list operation.getParameters() as parameter> "${parameter.getName()}"<#if parameter_has_next>,</#if></#list> });
        registerMuleBeanDefinitionParser("<@splitCamelCase>${operation.getName()}</@splitCamelCase>", parser);
        </#list>
    }
}
