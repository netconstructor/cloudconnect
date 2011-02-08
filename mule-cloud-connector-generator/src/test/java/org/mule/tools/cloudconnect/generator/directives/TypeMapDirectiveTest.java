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

public class TypeMapDirectiveTest extends AbstractDirectiveTest
{

    @Override
    protected TemplateDirectiveModel createDirective()
    {
        return new TypeMapDirective();
    }

    @Test
    public void integer() throws Exception
    {
        testDirective("int", "xsd:integer");
    }

    @Test
    public void date() throws Exception
    {
        testDirective("java.lang.Date", "xsd:date");
    }

    @Test
    public void str() throws Exception
    {
        testDirective("java.lang.String", "xsd:string");
    }

    @Test
    public void bool() throws Exception
    {
        testDirective("boolean", "xsd:boolean");
    }

    @Test
    public void boolClass() throws Exception
    {
        testDirective("java.lang.Boolean", "xsd:boolean");
    }

    @Test
    public void integerClass() throws Exception
    {
        testDirective("java.lang.Integer", "xsd:integer");
    }

}
