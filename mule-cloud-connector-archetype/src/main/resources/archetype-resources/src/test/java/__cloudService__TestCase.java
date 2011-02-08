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
package ${package};

import org.junit.Test;

import ${package}.${cloudService}CloudConnector;

public class ${cloudService}TestCase
{
    @Test
    public void invokeSomeMethodOnTheCloudConnector()
    {
    /*
        Add code that tests the cloud connector at the API level. This means that you'll
        instantiate your cloud connector directly, invoke one of its methods and assert
        you get the correct result.

        Example:

        ${cloudService}CloudConnector connector = new ${cloudService}CloudConnector();
        Object result = connector.someMethod("sample input");
        assertEquals("expected output", result);
     */
    }
}
