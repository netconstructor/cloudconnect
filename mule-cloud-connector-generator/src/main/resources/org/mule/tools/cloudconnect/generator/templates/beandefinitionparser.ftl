//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

package ${packageName};

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
        super("${method.getName()}", InvokerMessageProcessor.class);
    }

    @Override
    protected Class getBeanClass(Element element)
    {
        return InvokerMessageProcessor.class;
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
        else
        {
            builder.addPropertyValue("objectType", objectType);
        }

        List expressions = new ArrayList();
        <#list method.getParameters() as parameter>
        <#if parameter.getType().isArray()>
        Element <@uncapitalize>${parameter.getName()}</@uncapitalize>Element = DomUtils.getChildElementByTagName(element, "${parameter.getName()}");
        List <@uncapitalize>${parameter.getName()}</@uncapitalize>Elements = DomUtils.getChildElementsByTagName(<@uncapitalize>${parameter.getName()}</@uncapitalize>Element, "<@singularize>${parameter.getName()}</@singularize>");
        String[] <@uncapitalize>${parameter.getName()}</@uncapitalize>Array = new String[<@uncapitalize>${parameter.getName()}</@uncapitalize>Elements.size()];
        int <@uncapitalize>${parameter.getName()}</@uncapitalize>Index = 0;
        for( Element subElement : (List<Element>)<@uncapitalize>${parameter.getName()}</@uncapitalize>Elements )
        {
            <@uncapitalize>${parameter.getName()}</@uncapitalize>Array[<@uncapitalize>${parameter.getName()}</@uncapitalize>Index] = subElement.getTextContent();
            <@uncapitalize>${parameter.getName()}</@uncapitalize>Index++;
        }
        expressions.add(<@uncapitalize>${parameter.getName()}</@uncapitalize>Array);
        <#else>
        expressions.add(getAttributeValue(element, "${parameter.getName()}"));
        </#if>
        </#list>

        builder.addPropertyValue("arguments", expressions);
        builder.addPropertyValue("methodName", methodName);

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
