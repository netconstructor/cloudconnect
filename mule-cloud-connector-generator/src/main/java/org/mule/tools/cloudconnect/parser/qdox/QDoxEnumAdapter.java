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

import org.mule.tools.cloudconnect.model.JavaEnum;

import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.Type;

import java.util.ArrayList;
import java.util.List;

public class QDoxEnumAdapter implements JavaEnum
{

    private Type javaType;

    public QDoxEnumAdapter(Type javaType)
    {
        this.javaType = javaType;
    }

    public String getName()
    {
        return javaType.getJavaClass().getName();
    }

    public List<String> getValues()
    {
        List<String> list = new ArrayList<String>();

        JavaField[] fields = javaType.getJavaClass().getFields();
        for (int i = 0; i < fields.length; i++)
        {
            list.add(fields[i].getName());
        }

        return list;
    }

    @Override
    public int hashCode()
    {
        int hash = 1;
        hash = hash * 31 + getName().hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof QDoxEnumAdapter &&
                ((QDoxEnumAdapter) o).getName().equals(getName()));
    }
}
