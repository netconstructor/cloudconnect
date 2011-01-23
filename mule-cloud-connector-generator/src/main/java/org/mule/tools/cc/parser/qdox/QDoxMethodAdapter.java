/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cc.parser.qdox;

import org.mule.tools.cc.model.JavaMethod;
import org.mule.tools.cc.model.JavaParameter;
import org.mule.tools.cc.model.JavaVisitor;

import java.util.ArrayList;
import java.util.List;

public class QDoxMethodAdapter implements JavaMethod
{

    private com.thoughtworks.qdox.model.JavaMethod javaMethod;
    private List<JavaParameter> parameters;


    public QDoxMethodAdapter(com.thoughtworks.qdox.model.JavaMethod javaMethod)
    {
        this.javaMethod = javaMethod;
    }

    public String getName()
    {
        return this.javaMethod.getName();
    }

    public String getDescription()
    {
        return javaMethod.getComment();
    }

    public List<JavaParameter> getParameters()
    {
        if (parameters == null)
        {
            buildParameterCollection();
        }

        return this.parameters;
    }

    public void buildParameterCollection()
    {
        this.parameters = new ArrayList<JavaParameter>();

        com.thoughtworks.qdox.model.JavaParameter[] parameters = javaMethod.getParameters();
        for (int i = 0; i < parameters.length; i++)
        {
            this.parameters.add(new QDoxParameterAdapter(parameters[i]));
        }
    }

    public void accept(JavaVisitor<JavaMethod> visitor)
    {
        visitor.visit(this);
    }
}
