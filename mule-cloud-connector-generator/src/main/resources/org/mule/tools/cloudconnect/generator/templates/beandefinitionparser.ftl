//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

package ${class.getNamespaceHandlerPackage()};

import org.mule.config.spring.parsers.assembly.BeanAssembler;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.processor.InvokerMessageProcessor;
import org.mule.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import ${class.getPackage()}.${class.getName()};

public class ${method.getBeanDefinitionParserName()} extends ChildDefinitionParser
{
    private final Class<?> objectType = ${method.getParentClass().getName()}.class;
    private final String methodName = "${method.getName()}";

    public ${method.getBeanDefinitionParserName()}()
    {
        super("messageProcessor", ${method.getMessageProcessorName()}.class);
    }

    @Override
    protected Class getBeanClass(Element element)
    {
        return ${method.getMessageProcessorName()}.class;
    }

    @Override
    protected void parseChild(Element element, ParserContext parserContext, BeanDefinitionBuilder builder)
    {
        if (!StringUtils.isEmpty(element.getAttribute(getTargetPropertyConfiguration().getAttributeAlias(
            "config-ref"))))
        {
            builder.addPropertyReference("object",
                element.getAttribute(getTargetPropertyConfiguration().getAttributeAlias("config-ref")));
        }

        <#list method.getParameters() as parameter>
        <#if !parameter.getType().isArray()>
        builder.addPropertyValue("${parameter.getName()}", getAttributeValue(element, "${parameter.getName()}"));
        </#if>
        </#list>

        BeanAssembler assembler = getBeanAssembler(element, builder);
        postProcess(getParserContext(), assembler, element);
    }

    private String getAttributeValue(Element element, String attributeName)
    {
        if (!StringUtils.isEmpty(element.getAttribute(attributeName)))
        {
            return element.getAttribute(attributeName);
        }

        return null;
    }

}
