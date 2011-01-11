#set($D = '$')
package ${package}.config;

import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;

public class ${cloudService}NamespaceHandlerTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "${cloudServiceLower}-namespace-config.xml";
    }

    public void testSendMessageToFlow() throws Exception
    {
        /*

        To test your flow directly (i.e. without any inbound endpoints, declare a flow in
        ${cloudServiceLower}-namespace-config.xml and put the element from your
        cloud connector's namespace that you want to test into it.
        A proper example was put into ${cloudServiceLower}-namespace-config.xml

        Now you can send data to your test flow from the unit test:

        String payload = <your input to the flow here>;
        SimpleFlowConstruct flow = lookupFlowConstruct("theFlow");
        MuleEvent event = getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);
        assertEquals(<expected test output>, responseEvent.getMessage().getPayloadAsString());

        */
    }

    private SimpleFlowConstruct lookupFlowConstruct(String name)
    {
        return (SimpleFlowConstruct) muleContext.getRegistry().lookupFlowConstruct(name);
    }
}
