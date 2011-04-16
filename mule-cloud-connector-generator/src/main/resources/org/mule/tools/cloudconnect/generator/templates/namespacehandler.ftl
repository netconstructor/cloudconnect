//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

package ${class.getNamespaceHandlerPackage()};

import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;
import org.mule.config.spring.parsers.specific.InvokerMessageProcessorDefinitionParser;
import org.mule.config.spring.parsers.collection.ChildListEntryDefinitionParser;
import org.mule.config.spring.parsers.collection.ChildMapEntryDefinitionParser;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.generic.OrphanDefinitionParser;
import org.mule.config.spring.parsers.specific.MessageProcessorDefinitionParser;

import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.beans.factory.config.MapFactoryBean;

import ${class.getPackage()}.${class.getName()};

public class ${class.getNamespaceHandlerName()} extends AbstractPojoNamespaceHandler
{
    public void init()
    {
        <#if !class.getFactory()?has_content>
        registerPojo("config", ${class.getName()}.class);
        </#if>
        <#if class.getFactory()?has_content>
        registerMuleBeanDefinitionParser("config", new OrphanDefinitionParser(${class.getFactory().getFullyQualifiedName()}.class, true)).addIgnored("name");
        </#if>
        <#list class.getMethods() as method>

        <#if class.hasOAuth()>
        registerMuleBeanDefinitionParser("request-authorization", new RequestAuthorizationOperationDefinitionParser());
        </#if>

        <#if method.isOperation()>

        registerMuleBeanDefinitionParser("<@splitCamelCase>${method.getElementName()}</@splitCamelCase>", new ${method.getBeanDefinitionParserName()}());
        <#list method.getParameters() as parameter>
        <#if parameter.getType().isArray() || parameter.getType().isList()>
        registerMuleBeanDefinitionParser("${parameter.getName()}", new ChildDefinitionParser("${parameter.getName()}", ListFactoryBean.class));
        registerMuleBeanDefinitionParser("<@singularize>${parameter.getName()}</@singularize>", new ChildListEntryDefinitionParser("sourceList"));
        <#elseif parameter.getType().isMap()>
        registerMuleBeanDefinitionParser("${parameter.getName()}", new ChildDefinitionParser("${parameter.getName()}", MapFactoryBean.class));
        registerMuleBeanDefinitionParser("<@singularize>${parameter.getName()}</@singularize>", new ChildMapEntryDefinitionParser("sourceMap"));
        </#if>
        </#list>
        <#elseif method.isTransformer()>
        registerBeanDefinitionParser("<@splitCamelCase>${method.getElementName()}</@splitCamelCase>", new MessageProcessorDefinitionParser(${method.getMessageProcessorName()}.class));
        </#if>
        </#list>
    }
}
