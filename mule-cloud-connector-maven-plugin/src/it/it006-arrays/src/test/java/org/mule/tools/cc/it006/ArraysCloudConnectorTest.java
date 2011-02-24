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

package org.mule.tools.cc.it006;

import org.mule.api.MuleEvent;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;

import java.lang.String;

public class ArraysCloudConnectorTest extends FunctionalTestCase
{

    private static final String EMPTY_PAYLOAD = "";

    @Override
    protected String getConfigResources()
    {
        return "sum-all.xml";
    }

    public void testArray() throws Exception
    {
        String payload = EMPTY_PAYLOAD;
        SimpleFlowConstruct flow = lookupFlowConstruct("sumAllFlow");
        MuleEvent event = getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);

        assertEquals(15, responseEvent.getMessage().getPayload());
    }

    public void testGenericList() throws Exception
    {
        String payload = EMPTY_PAYLOAD;
        SimpleFlowConstruct flow = lookupFlowConstruct("sumAllFromGenericListFlow");
        MuleEvent event = getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);

        assertEquals(15, responseEvent.getMessage().getPayload());
    }

    public void testGenericListWithEnums() throws Exception
    {
        String payload = EMPTY_PAYLOAD;
        SimpleFlowConstruct flow = lookupFlowConstruct("sumAllColors");
        MuleEvent event = getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);

        assertEquals(24, responseEvent.getMessage().getPayload());
    }

    private SimpleFlowConstruct lookupFlowConstruct(String name)
    {
        return (SimpleFlowConstruct) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}