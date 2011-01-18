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
import com.thoughtworks.qdox.model.Type;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.and;
import static org.easymock.EasyMock.expect;

public class NamespaceHandlerGeneratorTestCase
{
    private EasyMockHelper easyMockHelper;
    private NamespaceHandlerGenerator generator;
    private boolean printGeneratedNamespaceHandler = false;

    @Before
    public void createNamespaceHandlerGenerator()
    {
        easyMockHelper = new EasyMockHelper();

        generator = new NamespaceHandlerGenerator();
        generator.setPackageName("org.mule.module.foo.config");
        generator.setClassName("FooNamespaceHandler");
    }

    @After
    public void tearDown()
    {
        easyMockHelper.reset();
    }

    @Test(expected = IllegalStateException.class)
    public void missingPackageName() throws Exception
    {
        generator.setPackageName(null);
        generator.generate(null);
    }

    @Test(expected = IllegalStateException.class)
    public void missingClassName() throws Exception
    {
        generator.setClassName(null);
        generator.generate(null);
    }

    @Test(expected = IllegalStateException.class)
    public void missingJavaClass() throws Exception
    {
        generator.setJavaClass(null);
        generator.generate(null);
    }

    @Test
    public void generateClassSkeleton() throws Exception
    {
        JavaClass javaClass = easyMockHelper.createMockClass("org.mule.module.foo", "FooCloudConnector", new JavaMethod[] { } );
        generator.setJavaClass(javaClass);

        easyMockHelper.replay();

        generateAndCompareTo("SkeletonNamespaceHandler.java.txt");

        easyMockHelper.verify();
    }
    
    @Test
    public void generateWithoutConfigElement() throws Exception
    {
        JavaParameter parameterOne = easyMockHelper.createMockParameter("symbol", "String");
        JavaParameter parameterTwo = easyMockHelper.createMockParameter("currency", "String");
        JavaParameter[] parameters = new JavaParameter[] { parameterOne, parameterTwo };

        JavaMethod method = easyMockHelper.createMockMethod("requestQuote", "Requests a quote", parameters);
        JavaMethod[] methods = new JavaMethod[] { method };

        JavaClass javaClass = easyMockHelper.createMockClass("org.mule.module.stockquote", "StockQuoteCloudConnector", methods);
        generator.setJavaClass(javaClass);

        generator.setPackageName("org.mule.module.stockquote.config");
        generator.setClassName("StockQuoteNamespaceHandler");

        easyMockHelper.replay();

        generateAndCompareTo("StockQuoteNamespaceHandler.java.txt");

        easyMockHelper.verify();
    }


    @Test
    public void generateWithConfigElement() throws Exception
    {
        JavaParameter parameter = easyMockHelper.createMockParameter("value", "String");
        JavaMethod method = easyMockHelper.createMockMethod("setApiKey", "", new JavaParameter[] { parameter });
        JavaClass javaClass = easyMockHelper.createMockClass("org.mule.module.mock", "ConfigElementCloudConnector", new JavaMethod[] { method });
        generator.setJavaClass(javaClass);

        generator.setPackageName("org.mule.module.mock.config");
        generator.setClassName("ConfigElementNamespaceHandler");

        easyMockHelper.replay();

        generateAndCompareTo("ConfigElementNamespaceHandler.java.txt");

        easyMockHelper.verify();
    }
    
    private void generateAndCompareTo(String filename) throws IOException
    {
        UnitTestUtils.runGeneratorAndCompareTo(generator, filename, printGeneratedNamespaceHandler);
    }
}
