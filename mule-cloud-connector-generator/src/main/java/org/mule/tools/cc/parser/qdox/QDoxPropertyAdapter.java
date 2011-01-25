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

import org.mule.tools.cc.model.JavaProperty;
import org.mule.tools.cc.model.JavaVisitor;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaField;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getEnumValues()
    {
        List<String> list = new ArrayList<String>();

        JavaField[] fields = javaProperty.getType().getJavaClass().getFields();
        for( int i = 0; i < fields.length; i++ )
        {
            list.add(fields[i].getName());
        }

        return list;
    }

    public void accept(JavaVisitor<JavaProperty> visitor)
    {
        visitor.visit(this);
    }
}
