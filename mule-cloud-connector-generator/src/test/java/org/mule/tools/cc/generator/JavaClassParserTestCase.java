/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cc.generator;

import java.io.InputStream;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JavaClassParserTestCase
{
    @Test(expected = IllegalArgumentException.class)
    public void javaFileDoesNotContainClass() throws Exception
    {
        InputStream input = UnitTestUtils.getTestResource("package-info.java.txt");
        new JavaClassParser().parse(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void javaFileContainsMoreThanOneClass() throws Exception
    {
        InputStream input = UnitTestUtils.getTestResource("MoreThanOneClass.java.txt");
        new JavaClassParser().parse(input);
    }

    @Test
    public void parseMethodWithoutParameters() throws Exception
    {
        InputStream input = UnitTestUtils.getTestResource("MethodWithoutParameters.java.txt");
        JavaClass javaClass = new JavaClassParser().parse(input);
        assertNotNull(javaClass);
        assertEquals(1, javaClass.getMethods().size());

        JavaMethod method = javaClass.getMethods().get(0);
        assertEquals("voidMethodWithoutParameters", method.getName());
        assertTrue(method.isPublic());
        assertEquals(0, method.getParameters().size());
    }

    @Test
    public void parseJavaFile() throws Exception
    {
        InputStream input = UnitTestUtils.getTestResource("SampleCloudConnector.java.txt");
        JavaClass javaClass = new JavaClassParser().parse(input);
        assertNotNull(javaClass);
        assertEquals(1, javaClass.getMethods().size());

        JavaMethod method = javaClass.getMethods().get(0);
        assertEquals("sampleMethod", method.getName());
        assertTrue(method.isPublic());
        assertEquals(1, method.getParameters().size());

        JavaMethodParameter parameter = method.getParameters().get(0);
        assertEquals("input", parameter.getName());
        assertEquals("String", parameter.getType());
    }
}
