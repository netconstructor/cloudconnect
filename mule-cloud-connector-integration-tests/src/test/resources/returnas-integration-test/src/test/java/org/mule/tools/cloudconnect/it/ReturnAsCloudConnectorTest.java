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

import java.lang.String;

import org.mule.api.MuleEvent;
import org.mule.api.transport.PropertyScope;

public class ReturnAsCloudConnectorTest extends AbstractCloudConnectorTest
{
    @Override
    protected String getConfigResources()
    {
        return "returnas.xml";
    }

    public void testPayload() throws Exception
    {
        MuleEvent responseEvent = runFlow("empty");

        assertEquals("empty", responseEvent.getMessage().getPayload());
    }

    public void testProperty() throws Exception
    {
        MuleEvent responseEvent = runFlow("zero");

        assertEquals("zero", responseEvent.getMessage().getProperty("zero", PropertyScope.OUTBOUND));
    }
}