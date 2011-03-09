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

public class BasicCloudConnectorTest extends AbstractCloudConnectorTest
{

    @Override
    protected String getConfigResources()
    {
        return "basic.xml";
    }

    public void testChar() throws Exception
    {
        runFlow("passthruCharFlow", 'c');
    }

    public void testString() throws Exception
    {
        runFlow("passthruStringFlow", "mulesoft");
    }

    public void testInteger() throws Exception
    {
        runFlow("passthruIntegerFlow", 3);
    }

    public void testFloat() throws Exception
    {
        runFlow("passthruFloatFlow", 3.14f);
    }

    public void testBoolean() throws Exception
    {
        runFlow("passthruBooleanFlow", true);
    }

    public void testLong() throws Exception
    {
        runFlow("passthruLongFlow", 3456443463342345734L);
    }
}