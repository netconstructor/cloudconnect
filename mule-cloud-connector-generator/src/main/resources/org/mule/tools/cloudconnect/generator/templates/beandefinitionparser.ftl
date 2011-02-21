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
import org.w3c.dom.Element;

import ${class.getPackage()}.${class.getName()};

public class ${method.getBeanDefinitionParserName()} extends ChildDefinitionParser
{
    private final Class<?> objectType = ${method.getParentClass().getName()}.class;
    private final String methodName = "${method.getName()}";
    private final String[] parameterNames = new String[] {<#list method.getParameters() as parameter> "${parameter.getName()}"<#if parameter_has_next>,</#if></#list> };

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

        List<String> expressions = new ArrayList<String>();
        if (parameterNames != null)
        {
            for (String parameterName : parameterNames)
            {
                if (!StringUtils.isEmpty(element.getAttribute(parameterName)))
                {
                    expressions.add(element.getAttribute(parameterName));
                }
                else
                {
                    expressions.add(null);
                }
            }
        }
        builder.addPropertyValue("arguments", expressions);
        builder.addPropertyValue("methodName", methodName);

        BeanAssembler assembler = getBeanAssembler(element, builder);
        postProcess(getParserContext(), assembler, element);
    }

}
