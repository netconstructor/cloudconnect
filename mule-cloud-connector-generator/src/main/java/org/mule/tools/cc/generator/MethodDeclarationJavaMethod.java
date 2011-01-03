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

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;

import java.util.ArrayList;
import java.util.List;

public class MethodDeclarationJavaMethod implements JavaMethod
{
    private MethodDeclaration methodDeclaration;

    public MethodDeclarationJavaMethod(MethodDeclaration methodDecl)
    {
        super();
        methodDeclaration = methodDecl;
    }

    public String getName()
    {
        return methodDeclaration.getName();
    }

    public List<JavaMethodParameter> getParameters()
    {
        List<JavaMethodParameter> parameters = new ArrayList<JavaMethodParameter>();
        for (Parameter parameter : methodDeclaration.getParameters())
        {
            parameters.add(new ParameterJavaMethodParameter(parameter));
        }
        return parameters;
    }

    public String getJavadoc()
    {
        return methodDeclaration.getJavaDoc().getContent();
    }

    public boolean isPublic()
    {
        return ModifierSet.isPublic(methodDeclaration.getModifiers());
    }
}
