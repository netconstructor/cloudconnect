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

import org.mule.tools.cloudconnect.model.JavaProperty;
import org.mule.tools.cloudconnect.model.JavaVisitor;

import com.thoughtworks.qdox.model.BeanProperty;

public class QDoxPropertyAdapter implements JavaProperty
{

    private BeanProperty javaProperty;

    protected QDoxPropertyAdapter(BeanProperty javaProperty)
    {
        this.javaProperty = javaProperty;
    }

    public String getName()
    {
        return this.javaProperty.getName();
    }

    public String getType()
    {
        return this.javaProperty.getType().getValue();
    }

    public String getDescription()
    {
        if( this.javaProperty.getMutator() != null )
        {
            return this.javaProperty.getMutator().getComment();
        }

        return null;
    }

    public boolean isEnum()
    {
        return javaProperty.getType().getJavaClass().isEnum();
    }

    public String getEnumName()
    {
        return javaProperty.getType().getJavaClass().getName();
    }

    public void accept(JavaVisitor<JavaProperty> visitor)
    {
        visitor.visit(this);
    }
}
