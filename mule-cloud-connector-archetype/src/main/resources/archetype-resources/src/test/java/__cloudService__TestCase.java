/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
