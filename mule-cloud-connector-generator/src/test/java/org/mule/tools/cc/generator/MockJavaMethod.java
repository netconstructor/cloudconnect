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

import java.util.Arrays;
import java.util.List;

public class MockJavaMethod implements JavaMethod
{
    private String name;
    private List<JavaMethodParameter> parameters;
    private String javadoc;

    public MockJavaMethod(String name)
    {
        this(name, null);
    }

    public MockJavaMethod(String name, String javadoc, JavaMethodParameter... parameters)
    {
        super();
        this.name = name;
        this.javadoc = javadoc;
        this.parameters = Arrays.asList(parameters);
    }

    public String getName()
    {
        return name;
    }

    public List<JavaMethodParameter> getParameters()
    {
        return parameters;
    }

    public String getJavadoc()
    {
        return javadoc;
    }
}
