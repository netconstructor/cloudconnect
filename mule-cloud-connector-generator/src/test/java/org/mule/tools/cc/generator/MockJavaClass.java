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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockJavaClass implements JavaClass
{
    private List<JavaMethod> methods = new ArrayList<JavaMethod>();
    private String packageName;
    private String className;

    public MockJavaClass(JavaMethod... javaMethods)
    {
        this(null, null, javaMethods);
    }

    public MockJavaClass(String pkgName, String name, JavaMethod... javaMethods)
    {
        super();
        packageName = pkgName;
        className = name;
        methods = Arrays.asList(javaMethods);
    }

    public List<JavaMethod> getMethods()
    {
        return methods;
    }

    public void addMethod(JavaMethod method)
    {
        methods.add(method);
    }

    public String getPackage()
    {
        return packageName;
    }

    public String getName()
    {
        return className;
    }
}
