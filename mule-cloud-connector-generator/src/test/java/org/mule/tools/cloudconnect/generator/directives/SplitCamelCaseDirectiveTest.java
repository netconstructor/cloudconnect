/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cloudconnect.generator.directives;

import freemarker.template.TemplateDirectiveModel;
import org.junit.Test;

public class SplitCamelCaseDirectiveTest extends AbstractDirectiveTest
{

    @Override
    protected TemplateDirectiveModel createDirective()
    {
        return new SplitCamelCaseDirective();
    }

    @Test
    public void splitCamelCase() throws Exception
    {
        testDirective("thisIsAMethodName", "this-is-amethod-name");
    }
}