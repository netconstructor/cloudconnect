/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mule.tools.cloudconnect.parser.qdox;

import org.mule.tools.cloudconnect.model.AbstractJavaType;
import org.mule.tools.cloudconnect.model.JavaAnnotation;
import org.mule.tools.cloudconnect.model.JavaType;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.Type;

import java.util.ArrayList;
import java.util.List;

public class QDoxTypeAdapter extends AbstractJavaType
{

    private static final Type LIST_TYPE = new Type("java.util.List");
    private static final Type MAP_TYPE = new Type("java.util.Map");
    private Type javaType;

    public QDoxTypeAdapter(Type javaType)
    {
        this.javaType = javaType;
    }

    public boolean isEnum()
    {
        return javaType.getJavaClass().isEnum();
    }

    public String getName()
    {
        if (isEnum())
        {
            return javaType.getJavaClass().getName();
        }

        return javaType.getFullyQualifiedName();
    }

    public boolean isArray()
    {
        return javaType.isArray();
    }

    public List<String> getValues()
    {
        List<String> list = new ArrayList<String>();

        JavaField[] fields = javaType.getJavaClass().getFields();
        for (int i = 0; i < fields.length; i++)
        {
            if (!"value".equals(fields[i].getName()))
            {
                list.add(fields[i].getName());
            }
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
        return (o instanceof QDoxTypeAdapter &&
                ((QDoxTypeAdapter) o).getName().equals(getName()));
    }

    public String getFullyQualifiedName(boolean generic)
    {
        if (generic)
        {
            return javaType.getGenericValue();
        }
        else
        {
            return javaType.getValue();
        }

    }

    public String getFullyQualifiedName()
    {
        return javaType.getValue();
    }

    public boolean isGeneric()
    {
        if (javaType.getActualTypeArguments() == null)
        {
            return false;
        }

        return javaType.getActualTypeArguments().length > 0;
    }

    public List<JavaType> getTypeArguments()
    {
        List<JavaType> javaTypes = new ArrayList<JavaType>();
        for (int i = 0; i < javaType.getActualTypeArguments().length; i++)
        {
            javaTypes.add(new QDoxTypeAdapter(javaType.getActualTypeArguments()[i]));
        }

        return javaTypes;
    }

    public boolean isList()
    {
        return javaType.isA(LIST_TYPE);
    }

    public boolean isMap()
    {
        return javaType.isA(MAP_TYPE);
    }


    public String getTransformerPackage()
    {
        return javaType.getJavaClass().getPackageName() + ".transformers";
    }
}
