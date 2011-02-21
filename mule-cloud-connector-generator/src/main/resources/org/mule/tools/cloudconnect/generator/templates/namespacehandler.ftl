//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

package ${packageName};

import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;
import org.mule.config.spring.parsers.specific.InvokerMessageProcessorDefinitionParser;
<#if class.getFactory()?has_content>
import org.mule.config.spring.parsers.generic.OrphanDefinitionParser;
</#if>
import ${class.getPackage()}.${class.getName()};

public class ${className} extends AbstractPojoNamespaceHandler
{
    public void init()
    {
        <#if class.hasProperties() && !class.getFactory()?has_content>
        registerPojo("config", ${class.getName()}.class);
        </#if>
        <#if class.getFactory()?has_content>
        registerMuleBeanDefinitionParser("config",
            new OrphanDefinitionParser(${class.getFactory().getFullyQualifiedName()}.class, true)).addIgnored("name");
        </#if>
        <#list class.getMethods() as method>
        <#if method.isOperation()>

        registerMuleBeanDefinitionParser("<@splitCamelCase>${method.getElementName()}</@splitCamelCase>", new ${method.getBeanDefinitionParserName()}());
        </#if>
        </#list>
    }
}
