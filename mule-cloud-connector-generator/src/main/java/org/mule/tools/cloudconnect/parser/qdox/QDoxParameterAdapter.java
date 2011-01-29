/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cloudconnect.parser.qdox;

import org.mule.tools.cloudconnect.model.JavaParameter;
import org.mule.tools.cloudconnect.model.JavaVisitor;

import com.thoughtworks.qdox.model.DocletTag;

public class QDoxParameterAdapter implements JavaParameter
{

    private com.thoughtworks.qdox.model.JavaParameter javaParameter;

    public QDoxParameterAdapter(com.thoughtworks.qdox.model.JavaParameter javaParameter)
    {
        this.javaParameter = javaParameter;
    }

    public String getName()
    {
        return javaParameter.getName();
    }

    public String getType()
    {
        return javaParameter.getType().getValue();
    }

    public boolean isEnum()
    {
        return javaParameter.getType().getJavaClass().isEnum();
    }

    public String getEnumName()
    {
        return javaParameter.getType().getJavaClass().getName();
    }

    public String getDescription()
    {
        if (javaParameter.getParentMethod() != null)
        {
            DocletTag[] doclets = javaParameter.getParentMethod().getTagsByName("param");

            for (int i = 0; i < doclets.length; i++)
            {
                if (doclets[i].getParameters()[0].equals(javaParameter.getName()))
                {
                    if (doclets[i].getValue().indexOf(' ') != -1)
                    {
                        return doclets[i].getValue().substring(doclets[i].getValue().indexOf(' '));
                    }
                }
            }
        }

        return null;
    }

    public void accept(JavaVisitor<JavaParameter> visitor)
    {
        visitor.visit(this);
    }
}
