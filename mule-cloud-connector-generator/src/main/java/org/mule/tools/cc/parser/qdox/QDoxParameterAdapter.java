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

import org.mule.tools.cc.model.JavaParameter;

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

    public String getDescription()
    {
        DocletTag[] doclets = javaParameter.getParentMethod().getTagsByName("param");

        for (int i = 0; i < doclets.length; i++)
        {
            if (doclets[i].getParameters()[0].equals(javaParameter.getName()))
            {
                return doclets[i].getValue().substring(doclets[i].getValue().indexOf(' '));
            }
        }

        return null;
    }
}
