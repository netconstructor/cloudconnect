#set($D = '$')
/*
 * ${D}Id${D}
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package ${package}.config;

import org.mule.tck.FunctionalTestCase;

public class ${cloudService}NamespaceHandlerTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "${cloudService}-namespace-config.xml";
    }

    public void testNamespace()
    {
        // TODO implement some tests
    }
}
