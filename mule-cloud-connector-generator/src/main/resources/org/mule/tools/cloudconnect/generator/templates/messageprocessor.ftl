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
import org.mule.api.transport.PropertyScope;
import org.mule.config.i18n.CoreMessages;
import org.mule.transformer.TransformerTemplate;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.NullPayload;
import org.mule.util.ClassUtils;
import org.mule.util.TemplateParser;
import org.mule.util.TemplateParser.PatternInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            try
            {
                object = new ${class.getName()}();

                muleContext.getRegistry().registerObject("${class.getFullyQualifiedName()}", object);
            }
            catch (RegistrationException e)
            {
                throw new InitialisationException(
                    CoreMessages.initialisationFailure("Cannot create a new instance of '${class.getFullyQualifiedName()}'"), this);
            }
        }
    }

    public MuleEvent process(MuleEvent event) throws MuleException
    {
        MuleMessage message = event.getMessage();
        MuleEvent resultEvent = event;
        <#list method.getParameters() as parameter>
        ${parameter.getType().getFullyQualifiedName(true)} <@uncapitalize>${parameter.getName()}</@uncapitalize><#if !parameter.getType().isPrimitive()> = null</#if>;
        </#list>

        <#if class.hasOAuth()>
        if (object.${class.getOAuthAuthorizationCodeProperty().getAccessorName()}() == null)
            throw new IllegalStateException("This connector has not been authorized yet");

        if( object.${class.getOAuthAccessTokenExpirationProperty().getAccessorName()}() < Calendar.getInstance().getTimeInMillis() )
        {
            logger.info("Access token has a expired!");
            object.${class.getOAuthAccessTokenProperty().getMutatorName()}(null);
        }

        if (object.${class.getOAuthAccessTokenProperty().getAccessorName()}() == null) {
            logger.info("Requesting access token...");
            URL url;
            try {
                StringBuffer query = new StringBuffer();
                query.append("${class.getOAuthAccessTokenUrl()}");
                query.append("?client_id=" + object.${class.getOAuthClientIdProperty().getAccessorName()}());
                query.append("&redirect_uri=" + URLEncoder.encode(object.${class.getOAuthRedirectUriProperty().getAccessorName()}()));
                query.append("&client_secret=" + object.${class.getOAuthClientSecretProperty().getAccessorName()}());
                query.append("&code=" + object.${class.getOAuthAuthorizationCodeProperty().getAccessorName()}());
                logger.debug("Requesting token to: " + query.toString());

                url = new URL(query.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Buffer the result into a string
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }
                rd.close();

                logger.info("Response Body: " + response.toString());
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("The server did not respond with 200 while retrieving the access token. Response: " + conn.getResponseMessage());
                }

                conn.disconnect();

                String regex = "access_token=(\\S*?)(&expires=(\\S*))?";

                Matcher matcher = Pattern.compile(regex).matcher(response.toString());
                if (matcher.matches()) {
                    try {
                        logger.debug("Access Token obtained, it expires in " + matcher.group(3) + " seconds");
                        String accessToken = URLDecoder.decode(matcher.group(1), "UTF_8");
                        Calendar expiration = Calendar.getInstance();
                        expiration.add(Calendar.SECOND, Integer.parseInt(matcher.group(3)) - 5);

                        object.${class.getOAuthAccessTokenProperty().getMutatorName()}(accessToken);
                        object.${class.getOAuthAccessTokenExpirationProperty().getMutatorName()}(expiration.getTimeInMillis());
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                } else {
                    throw new RuntimeException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid URL: ${class.getOAuthAccessTokenUrl()}", e);
            } catch (IOException e) {
                throw new RuntimeException("An I/O error ocurred trying to retrieve the access token", e);
            }
        }
        </#if>

        <#if method.hasParameters()>
        try
        {
        <#list method.getParameters() as parameter>
        <#if !parameter.getType().isPrimitive()>
            if( this.${parameter.getName()} != null )
            {
        </#if>
        <#if parameter.getType().isList()>
            <@uncapitalize>${parameter.getName()}</@uncapitalize> = (${parameter.getType().getFullyQualifiedName(true)})transformList(evaluateExpressionCandidate(this.${parameter.getName()}, message), <@typeClass>${parameter.getType().getTypeArguments().get(0).getFullyQualifiedName()}</@typeClass>);
        <#elseif parameter.getType().isMap()>
            <@uncapitalize>${parameter.getName()}</@uncapitalize> = (${parameter.getType().getFullyQualifiedName(true)})transformMap(evaluateExpressionCandidate(this.${parameter.getName()}, message), <@typeClass>${parameter.getType().getTypeArguments().get(0).getFullyQualifiedName()}</@typeClass>, <@typeClass>${parameter.getType().getTypeArguments().get(1).getFullyQualifiedName()}</@typeClass>);
        <#else>
            <@uncapitalize>${parameter.getName()}</@uncapitalize> = (${parameter.getType().getFullyQualifiedName(true)})transformArgument(evaluateExpressionCandidate(this.${parameter.getName()}, message), <@typeClass>${parameter.getType().getFullyQualifiedName(true)}</@typeClass>);
        </#if>
        <#if !parameter.getType().isPrimitive()>
            }
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
        </#if>

        try
        {
            <#if !method.getReturnType().isVoid()>Object result = </#if>object.${method.getName()}( <#list method.getParameters() as parameter>${parameter.getName()}<#if parameter_has_next>,</#if></#list>);
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
        if (arg.getClass().isArray())
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

    private <U,K,V> Map<K,V> transformMap(U arg, Class<K> genericKey, Class<V> genericObj) throws TransformerException, InstantiationException, IllegalAccessException
    {
        Map<?,?> collection = (Map<?,?>)arg;
        Map<K,V> newCollection = new HashMap<K,V>();

        for (Object key : collection.keySet())
        {
            Object object = collection.get(key);
            newCollection.put(transformArgument(key, genericKey), transformArgument(object, genericObj));
        }
        return (Map<K,V>) newCollection;
    }

    protected MuleEvent createResultEvent(MuleEvent event, Object result) throws MuleException
    {
        if (result instanceof MuleMessage)
        {
            return new DefaultMuleEvent((MuleMessage) result, event);
        }
        else if (result != null)
        {
            <#if !method.returnAsProperty()>
            event.getMessage().applyTransformers(
                event,
                Collections.<Transformer> singletonList(new TransformerTemplate(
                    new TransformerTemplate.OverwitePayloadCallback(result))));
            return event;
            <#else>
            event.getMessage().setProperty("${method.getElementName()}", result, PropertyScope.OUTBOUND);
            return event;
            </#if>
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

    public void setObject(${class.getName()} object)
    {
        this.object = object;
    }
}
