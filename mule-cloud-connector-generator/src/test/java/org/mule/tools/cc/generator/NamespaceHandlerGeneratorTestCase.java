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
        MockJavaClass javaClass = new MockJavaClass("org.mule.module.foo", "FooCloudConnector");
        generator.setJavaClass(javaClass);

        generateAndCompareTo("SkeletonNamespaceHandler.java.txt");
    }

    private void generateAndCompareTo(String filename) throws IOException
    {
        UnitTestUtils.runGeneratorAndCompareTo(generator, filename, printGeneratedNamespaceHandler);
    }
}
