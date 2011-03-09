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

package org.mule.tools.cloudconnect.it;

import org.mule.api.MuleEvent;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.AbstractMuleTestCase;

import java.lang.String;

import junit.framework.Assert;

public class MapsCloudConnectorTest extends FunctionalTestCase
{

    private static final String EMPTY_PAYLOAD = "";

    @Override
    protected String getConfigResources()
    {
        return "maps.xml";
    }

    public void testMap() throws Exception
    {
        String payload = EMPTY_PAYLOAD;
        SimpleFlowConstruct flow = lookupFlowConstruct("countKeysFlow");
        MuleEvent event = AbstractMuleTestCase.getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);

        assertEquals(4, responseEvent.getMessage().getPayload());
    }

    public void testMapWithEnums() throws Exception
    {
        String payload = EMPTY_PAYLOAD;
        SimpleFlowConstruct flow = lookupFlowConstruct("countKeysWithEnumFlow");
        MuleEvent event = AbstractMuleTestCase.getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);

        assertEquals(3, responseEvent.getMessage().getPayload());
    }

    public void testExpressionOnKey() throws Exception
    {
        String payload = EMPTY_PAYLOAD;
        SimpleFlowConstruct flow = lookupFlowConstruct("getFirstKeyFlow");
        MuleEvent event = AbstractMuleTestCase.getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);

        assertEquals("company", responseEvent.getMessage().getPayload());
    }

    public void testExpressionOnValue() throws Exception
    {
        String payload = EMPTY_PAYLOAD;
        SimpleFlowConstruct flow = lookupFlowConstruct("getFirstValueFlow");
        MuleEvent event = AbstractMuleTestCase.getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);

        assertEquals("mulesoft", responseEvent.getMessage().getPayload());
    }

    private SimpleFlowConstruct lookupFlowConstruct(String name)
    {
        return (SimpleFlowConstruct) AbstractMuleTestCase.muleContext.getRegistry().lookupFlowConstruct(name);
    }

}