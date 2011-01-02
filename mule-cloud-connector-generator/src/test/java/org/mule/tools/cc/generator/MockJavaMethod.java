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
    private boolean isPublic;

    public MockJavaMethod(String name)
    {
        this(name, null, true);
    }

    public MockJavaMethod(String name, String javadoc, JavaMethodParameter... parameters)
    {
        this(name, javadoc, true, parameters);
    }

    public MockJavaMethod(String name, String javadoc, boolean isPublic, JavaMethodParameter... parameters)
    {
        super();
        this.name = name;
        this.javadoc = javadoc;
        this.isPublic = isPublic;
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

    public boolean isPublic()
    {
        return isPublic;
    }
}
