//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

package ${packageName};

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

    <#list method.getParameters() as parameter>
    protected Object<#if parameter.getType().isArray()>[]</#if> ${parameter.getName()};
    </#list>

    protected PatternInfo patternInfo = TemplateParser.createMuleStyleParser().getStyle();
    protected ExpressionManager expressionManager;
    protected MuleContext muleContext;

    public void initialise() throws InitialisationException
    {
        if (object == null)
        {
            lookupObjectInstance();
        }

        expressionManager = muleContext.getExpressionManager();
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
        <#list method.getParameters() as parameter>
        ${parameter.getType().getFullyQualifiedName(true)} <@uncapitalize>${parameter.getName()}</@uncapitalize>;
        </#list>

        try
        {
        <#list method.getParameters() as parameter>
        <#if parameter.getType().isList()>
            <@uncapitalize>${parameter.getName()}</@uncapitalize> = (${parameter.getType().getFullyQualifiedName(true)})transformList(evaluateExpressionCandidate(this.${parameter.getName()}, message), <@typeClass>${parameter.getType().getTypeArguments().get(0).getFullyQualifiedName()}</@typeClass>);
        <#else>
            <@uncapitalize>${parameter.getName()}</@uncapitalize> = (${parameter.getType().getFullyQualifiedName(true)})transformArgument(evaluateExpressionCandidate(this.${parameter.getName()}, message), <@typeClass>${parameter.getType().getFullyQualifiedName(true)}</@typeClass>);
        </#if>
        </#list>
        }
        catch (TransformerException e)
        {
            throw new MessagingException(event, e);
        }
        catch (IllegalAccessException e)
        {
            throw new MessagingException(event, e);
        }
        catch (InstantiationException e)
        {
            throw new MessagingException(event, e);
        }

        try
        {
            Object result = object.${method.getName()}( <#list method.getParameters() as parameter>${parameter.getName()}<#if parameter_has_next>,</#if></#list>);
            <#if !method.getReturnType().isVoid()>
            resultEvent = createResultEvent(event, result);
            </#if>
        }
        catch (Exception e)
        {
            throw new MessagingException(CoreMessages.failedToInvoke(object.toString()), event, e);
        }
        return resultEvent;
    }

    protected <U,V> V evaluateArgument(MuleEvent event, U template, Class<V> type) throws MessagingException
    {
        MuleMessage message = event.getMessage();
        try
        {
            return (V) transformArgument(evaluateExpressionCandidate(template, message), type);
        }
        catch (TransformerException e)
        {
            throw new MessagingException(event, e);
        }
        catch (IllegalAccessException e)
        {
            throw new MessagingException(event, e);
        }
        catch (InstantiationException e)
        {
            throw new MessagingException(event, e);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T evaluateExpressionCandidate(T expressionCandidate, MuleMessage message)
        throws TransformerException
    {
        if (expressionCandidate.getClass().isArray())
        {
            Object[] collectionTemplate = (Object[])expressionCandidate;
            Collection<Object> newCollection = new ArrayList<Object>();
            for( int i = 0; i < collectionTemplate.length; i++ )
            {
                newCollection.add(evaluateExpressionCandidate(collectionTemplate[i], message));
            }
            return (T) newCollection.toArray();
        }
        else if (expressionCandidate instanceof Collection<?>)
        {
            Collection<Object> collectionTemplate = (Collection<Object>) expressionCandidate;
            Collection<Object> newCollection = new ArrayList<Object>();
            for (Object object : collectionTemplate)
            {
                newCollection.add(evaluateExpressionCandidate(object, message));
            }
            return (T)newCollection;
        }
        else if (expressionCandidate instanceof Map<?, ?>)
        {
            Map<Object, Object> mapTemplate = (Map<Object, Object>) expressionCandidate;
            Map<Object, Object> newMap = new HashMap<Object, Object>();
            for (Entry<Object, Object> entry : mapTemplate.entrySet())
            {
                newMap.put(evaluateExpressionCandidate(entry.getKey(), message), evaluateExpressionCandidate(
                    entry.getValue(), message));
            }
            return (T)newMap;
        }
        else if (expressionCandidate instanceof String[])
        {
            String[] stringArrayTemplate = (String[]) expressionCandidate;
            Object[] newArray = new String[stringArrayTemplate.length];
            for (int j = 0; j < stringArrayTemplate.length; j++)
            {
                newArray[j] = evaluateExpressionCandidate(stringArrayTemplate[j], message);
            }
            return (T)newArray;
        }
        if (expressionCandidate instanceof String)
        {
            T arg;
            String expression = (String) expressionCandidate;
            // If string contains is a single expression then evaluate otherwise
            // parse. We can't use parse() always because that will convert
            // everything to a string
            if (expression.startsWith(patternInfo.getPrefix())
                && expression.endsWith(patternInfo.getSuffix()))
            {
                arg = (T)expressionManager.evaluate(expression, message);
            }
            else
            {
                arg = (T)expressionManager.parse(expression, message);
            }

            // If expression evaluates to a MuleMessage then use it's payload
            if (arg instanceof MuleMessage)
            {
                arg = (T)(((MuleMessage) arg).getPayload());
            }
            return arg;
        }
        else
        {
            // Not an expression so use object itself
            return expressionCandidate;
        }
    }

    private <U,V> V transformArgument(U arg, Class<V> type) throws TransformerException, IllegalAccessException, InstantiationException
    {
        if(
        else if (arg.getClass().isArray())
        {
            Object[] collection = (Object[])arg;
            Object newCollection = Array.newInstance(type.getComponentType(), collection.length);
            for( int i = 0; i < collection.length; i++ )
            {
                Array.set(newCollection, i, transformArgument(collection[i], type.getComponentType()));
            }

            return (V)newCollection;
        }
        else if (!(type.isAssignableFrom(arg.getClass())))
        {
            DataType<?> source = DataTypeFactory.create(arg.getClass());
            DataType<?> target = DataTypeFactory.create(type);
            // Throws TransformerException if no suitable transformer is found
            Transformer t = muleContext.getRegistry().lookupTransformer(source, target);
            V result = (V)t.transform(arg);
            return result;
        }

        return (V)arg;
    }

    private <U,V> List<V> transformList(U arg, Class<V> generic) throws TransformerException, InstantiationException, IllegalAccessException
    {
        List<?> collection = (List<?>)arg;
        List<V> newCollection = new ArrayList<V>();

        for (Object object : collection)
        {
            newCollection.add(transformArgument(object, generic));
        }
        return (List<V>) newCollection;
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

    <#list method.getParameters() as parameter>
    public void set<@capitalize>${parameter.getName()}</@capitalize>(Object<#if parameter.getType().isArray()>[]</#if> value)
    {
        this.${parameter.getName()} = value;
    }

    </#list>

}
