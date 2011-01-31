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
import org.mule.tools.cloudconnect.model.JavaMethod;
import org.mule.tools.cloudconnect.model.JavaProperty;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QDoxClassAdapter implements org.mule.tools.cloudconnect.model.JavaClass
{

    private static final String CLASS_PROPERTY_NAME = "class";
    private JavaClass javaClass;
    private List<JavaProperty> properties;
    private List<JavaMethod> methods;
    private Set<JavaEnum> enums;

    protected QDoxClassAdapter(JavaClass javaClass)
    {
        this.javaClass = javaClass;

        buildPropertyCollection();
        buildMethodCollection();
        buildEnumCollection();
    }

    public String getName()
    {
        return javaClass.getName();
    }

    public String getPackage()
    {
        return javaClass.getPackage().getName();
    }

    public String getDescription()
    {
        return javaClass.getComment();
    }

    public List<JavaProperty> getProperties()
    {
        if (this.properties == null)
        {
            buildPropertyCollection();
        }

        return this.properties;
    }

    public boolean hasProperties()
    {
        return getProperties().size() > 0;
    }

    private void buildPropertyCollection()
    {
        this.properties = new ArrayList<JavaProperty>();

        BeanProperty[] properties = javaClass.getBeanProperties(true);
        for (int i = 0; i < properties.length; i++)
        {
            if (isValidProperty(properties[i]))
            {
                this.properties.add(new QDoxPropertyAdapter(properties[i]));
            }
        }
    }

    private boolean isValidProperty(BeanProperty property)
    {
        return !CLASS_PROPERTY_NAME.equals(property.getName());
    }

    public List<JavaMethod> getMethods()
    {
        if (this.methods == null)
        {
            buildMethodCollection();
        }

        return this.methods;
    }

    public Set<JavaEnum> getEnums()
    {
        if (this.enums == null)
        {
            buildEnumCollection();
        }

        return this.enums;
    }

    private void buildMethodCollection()
    {
        this.methods = new ArrayList<JavaMethod>();

        com.thoughtworks.qdox.model.JavaMethod[] methods = javaClass.getMethods(true);
        for (int i = 0; i < methods.length; i++)
        {
            this.methods.add(new QDoxMethodAdapter(methods[i]));
        }

    }

    private void buildEnumCollection()
    {
        this.enums = new HashSet<JavaEnum>();

        BeanProperty[] properties = javaClass.getBeanProperties(true);
        for (int i = 0; i < properties.length; i++)
        {
            if (isValidProperty(properties[i]) &&
                properties[i].getType().getJavaClass().isEnum())
            {
                this.enums.add(new QDoxEnumAdapter(properties[i].getType()));
            }
        }

        com.thoughtworks.qdox.model.JavaMethod[] methods = javaClass.getMethods(true);
        for (int i = 0; i < methods.length; i++)
        {
            com.thoughtworks.qdox.model.JavaParameter[] parameters = methods[i].getParameters();
            for (int j = 0; j < parameters.length; j++)
            {
                if (parameters[j].getType().getJavaClass().isEnum())
                {
                    this.enums.add(new QDoxEnumAdapter(parameters[j].getType()));
                }
            }
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 1;
        hash = hash * 31 + getName().hashCode();
        hash = hash * 31 + getPackage().hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof QDoxClassAdapter &&
                ((QDoxClassAdapter) o).getName().equals(getName()) &&
                ((QDoxClassAdapter) o).getPackage().equals(getPackage()));
    }
}
