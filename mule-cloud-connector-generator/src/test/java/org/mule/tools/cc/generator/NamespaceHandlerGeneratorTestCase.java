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

import java.io.IOException;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NamespaceHandlerGeneratorTestCase
{
    private NamespaceHandlerGenerator generator;
    private boolean printGeneratedNamespaceHandler = false;

    @Before
    public void createNamespaceHandlerGenerator()
    {
        generator = new NamespaceHandlerGenerator();
        generator.setPackageName("org.mule.module.foo.config");
        generator.setClassName("FooNamespaceHandler");
    }

    @Test
    public void generateClassSkeleton() throws Exception
    {
        JavaClass javaClass = UnitTestUtils.createMockClass("org.mule.module.foo", "FooCloudConnector", new JavaMethod[] { } );
        generator.setJavaClass(javaClass);

        generateAndCompareTo("SkeletonNamespaceHandler.java.txt");
    }
    
    @Test
    public void generateWithoutConfigElement() throws Exception
    {
        JavaParameter parameterOne = UnitTestUtils.createMockParameter("symbol", "String");
        JavaParameter parameterTwo = UnitTestUtils.createMockParameter("currency", "String");
        JavaParameter[] parameters = new JavaParameter[] { parameterOne, parameterTwo };

        JavaMethod method = UnitTestUtils.createMockMethod("requestQuote", "Requests a quote", parameters);
        JavaMethod[] methods = new JavaMethod[] { method };

        JavaClass javaClass = UnitTestUtils.createMockClass("org.mule.module.stockquote", "StockQuoteCloudConnector", methods);
        generator.setJavaClass(javaClass);

        generator.setPackageName("org.mule.module.stockquote.config");
        generator.setClassName("StockQuoteNamespaceHandler");

        generateAndCompareTo("StockQuoteNamespaceHandler.java.txt");
    }


    @Test
    public void generateWithConfigElement() throws Exception
    {
        JavaParameter parameter = UnitTestUtils.createMockParameter("value", "String");
        JavaMethod method = UnitTestUtils.createMockMethod("setApiKey", "", new JavaParameter[] { parameter });
        JavaClass javaClass = UnitTestUtils.createMockClass("org.mule.module.mock", "ConfigElementCloudConnector", new JavaMethod[] { method });
        generator.setJavaClass(javaClass);

        generator.setPackageName("org.mule.module.mock.config");
        generator.setClassName("ConfigElementNamespaceHandler");

        generateAndCompareTo("ConfigElementNamespaceHandler.java.txt");
    }
    
    private void generateAndCompareTo(String filename) throws IOException
    {
        UnitTestUtils.runGeneratorAndCompareTo(generator, filename, printGeneratedNamespaceHandler);
    }
}
