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
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.registry.RegistrationException;
import org.mule.config.i18n.CoreMessages;
import org.mule.util.TemplateParser;
import org.mule.util.TemplateParser.PatternInfo;
import org.mule.transport.NullPayload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ${class.getPackage()}.${class.getName()};

public class SetAuthorizationCodeMessageProcessor implements MessageProcessor, Initialisable, MuleContextAware
{
    protected final transient Log logger = LogFactory.getLog(getClass());
    protected ${class.getName()} object;
    protected Object code;
    protected MuleContext muleContext;
    protected ExpressionManager expressionManager;
    protected PatternInfo patternInfo = TemplateParser.createMuleStyleParser().getStyle();

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

        try
        {
            String authCode;
            String expression = (String)this.code;
            if (expression.startsWith(patternInfo.getPrefix())
                && expression.endsWith(patternInfo.getSuffix()))
            {
                authCode = (String)expressionManager.evaluate(expression, message);
            }
            else
            {
                authCode = (String)expressionManager.parse(expression, message);
            }

            object.${class.getOAuthAuthorizationCodeProperty().getMutatorName()}(authCode);

            return new DefaultMuleEvent(new DefaultMuleMessage(NullPayload.getInstance(),
                   event.getMuleContext()), event);

        }
        catch (Exception e)
        {
            throw new MessagingException(CoreMessages.failedToInvoke(object.toString()), event, e);
        }
    }

    @Override
    public String toString()
    {
        return String.format(
            "SetAuthenticationCodeMessageProcessor [object=%s]", object);
    }

    public void setMuleContext(MuleContext context)
    {
        this.muleContext = context;
    }


    public void setObject(${class.getName()} object)
    {
        this.object = object;
    }

    public void setCode(Object code)
    {
        this.code = code;
    }
}
