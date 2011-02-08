/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 */

#set($D='$')
package ${package}.config;

import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;

public class ${cloudService}NamespaceHandlerTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return"${cloudServiceLower}-namespace-config.xml";
    }

    public void testSendMessageToFlow()throws Exception
    {
    /*
        This test case tests your Mule integration.

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
        return(SimpleFlowConstruct)muleContext.getRegistry().lookupFlowConstruct(name);
    }
}
