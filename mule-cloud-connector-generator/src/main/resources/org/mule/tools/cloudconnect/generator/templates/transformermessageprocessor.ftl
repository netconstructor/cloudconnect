//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

package ${class.getNamespaceHandlerPackage()};

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.api.MessagingException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.context.MuleContextAware;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.api.transformer.DataType;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transformer.TransformerTemplate;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.NullPayload;
import org.mule.util.ClassUtils;
import org.mule.util.TemplateParser;
import org.mule.util.TemplateParser.PatternInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ${class.getPackage()}.${class.getName()};

public class ${method.getMessageProcessorName()} implements MessageProcessor, Initialisable, MuleContextAware
{
    protected final transient Log logger = LogFactory.getLog(getClass());
    protected ${class.getName()} object;
    protected MuleContext muleContext;

    public void initialise() throws InitialisationException
    {
        if (object == null)
        {
            lookupObjectInstance();
        }
    }

    protected void lookupObjectInstance() throws InitialisationException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug(String.format(
                "No object instance specified.  Looking up single instance of type %s in mule registry",
                object.getClass()));
        }

        try
        {
            object = muleContext.getRegistry().lookupObject(${class.getName()}.class);
        }
        catch (RegistrationException e)
        {
            throw new InitialisationException(
                CoreMessages.initialisationFailure(String.format(
                    "Multiple instances of '%s' were found in the registry so you need to configure a specific instance",
                    object.getClass())), this);
        }
        if (object == null)
        {
            throw new InitialisationException(CoreMessages.initialisationFailure(String.format(
                "No instance of '%s' was found in the registry", object.getClass())), this);

        }
    }

    public MuleEvent process(MuleEvent event) throws MuleException
    {
        MuleMessage message = event.getMessage();
        MuleEvent resultEvent = event;

        try
        {
            Object result = object.${method.getName()}((${method.getParameters().get(0).getType().getFullyQualifiedName()})message.getPayload());
            resultEvent = createResultEvent(event, result);
        }
        catch (Exception e)
        {
            throw new MessagingException(CoreMessages.failedToInvoke(object.toString()), event, e);
        }

        return resultEvent;
    }

    protected MuleEvent createResultEvent(MuleEvent event, Object result) throws MuleException
    {
        if (result instanceof MuleMessage)
        {
            return new DefaultMuleEvent((MuleMessage) result, event);
        }
        else if (result != null)
        {
            event.getMessage().applyTransformers(
                event,
                Collections.<Transformer> singletonList(new TransformerTemplate(
                    new TransformerTemplate.OverwitePayloadCallback(result))));
            return event;
        }
        else
        {
            return new DefaultMuleEvent(new DefaultMuleMessage(NullPayload.getInstance(),
                event.getMuleContext()), event);
        }
    }

    @Override
    public String toString()
    {
        return String.format(
            "${method.getMessageProcessorName()} [object=%s, methodName=${method.getName()}]", object);
    }

    public void setMuleContext(MuleContext context)
    {
        this.muleContext = context;
    }

}
