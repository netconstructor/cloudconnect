package org.mule.tools.cc.it006;

import org.mule.api.MuleEvent;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;

public class ArraysCloudConnectorTest extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "sum-all.xml";
    }

    public void testSearchTags() throws Exception
    {
        String payload = "";
        SimpleFlowConstruct flow = lookupFlowConstruct("sumAllFlow");
        MuleEvent event = getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);

        assertEquals(15, responseEvent.getMessage().getPayload());
    }

    private SimpleFlowConstruct lookupFlowConstruct(String name)
    {
        return (SimpleFlowConstruct) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}