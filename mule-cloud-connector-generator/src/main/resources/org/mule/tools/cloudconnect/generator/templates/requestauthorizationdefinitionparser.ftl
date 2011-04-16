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

public class RequestAuthorizationOperationDefinitionParser extends ChildDefinitionParser
{
    public RequestAuthorizationOperationDefinitionParser()
    {
        super("messageProcessor", RequestAuthorizationMessageProcessor.class);
    }

    @Override
    protected Class getBeanClass(Element element)
    {
        return RequestAuthorizationMessageProcessor.class;
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
