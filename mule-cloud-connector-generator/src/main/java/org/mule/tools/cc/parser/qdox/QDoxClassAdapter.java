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

import org.mule.tools.cc.model.JavaEnum;
import org.mule.tools.cc.model.JavaMethod;
import org.mule.tools.cc.model.JavaProperty;
import org.mule.tools.cc.model.JavaVisitor;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QDoxClassAdapter implements org.mule.tools.cc.model.JavaClass
{

    private static final String CLASS_PROPERTY_NAME = "class";
    private static final String OBJECT_CLASS_NAME = "Object";
    private JavaClass javaClass;
    private List<JavaProperty> properties;
    private List<JavaMethod> operations;
    private Set<JavaEnum> enums;

    protected QDoxClassAdapter(JavaClass javaClass)
    {
        this.javaClass = javaClass;

        buildPropertyCollection();
        buildOperationCollection();
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

    public List<JavaMethod> getOperations()
    {
        if (this.operations == null)
        {
            buildOperationCollection();
        }

        return this.operations;
    }

    public Set<JavaEnum> getEnums()
    {
        if (this.enums == null)
        {
            buildEnumCollection();
        }

        return this.enums;
    }

    private void buildOperationCollection()
    {
        this.operations = new ArrayList<JavaMethod>();

        com.thoughtworks.qdox.model.JavaMethod[] methods = javaClass.getMethods(true);
        for (int i = 0; i < methods.length; i++)
        {
            if (isValidMethod(methods[i]))
            {
                this.operations.add(new QDoxMethodAdapter(methods[i]));
            }
        }

    }

    private boolean isValidMethod(com.thoughtworks.qdox.model.JavaMethod method)
    {
        return method.isPublic()
               && !method.isStatic()
               && !method.isPropertyAccessor()
               && !method.isPropertyMutator()
               && !method.isConstructor()
               && !OBJECT_CLASS_NAME.equals(method.getParentClass().getName());
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
            if (isValidMethod(methods[i]))
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
    }

    public void accept(JavaVisitor<org.mule.tools.cc.model.JavaClass> visitor)
    {
        visitor.visit(this);
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
