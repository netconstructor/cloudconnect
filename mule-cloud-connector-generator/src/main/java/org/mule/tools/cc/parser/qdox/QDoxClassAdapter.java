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
import org.mule.tools.cc.model.JavaProperty;
import org.mule.tools.cc.model.JavaVisitor;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;

import java.util.ArrayList;
import java.util.List;

public class QDoxClassAdapter implements org.mule.tools.cc.model.JavaClass
{

    private static final String CLASS_PROPERTY_NAME = "class";
    private static final String OBJECT_CLASS_NAME = "Object";
    private JavaClass javaClass;
    private List<JavaProperty> properties;
    private List<JavaMethod> operations;

    protected QDoxClassAdapter(JavaClass javaClass)
    {
        this.javaClass = javaClass;
    }

    public String getName()
    {
        return javaClass.getName();
    }

    public String getPackage()
    {
        return javaClass.getPackage();
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
            if (!CLASS_PROPERTY_NAME.equals(properties[i].getName()))
            {
                this.properties.add(new QDoxPropertyAdapter(properties[i]));
            }
        }
    }

    public List<JavaMethod> getOperations()
    {
        if (this.operations == null)
        {
            buildOperationCollection();
        }

        return this.operations;
    }

    private void buildOperationCollection()
    {
        this.operations = new ArrayList<JavaMethod>();

        com.thoughtworks.qdox.model.JavaMethod[] methods = javaClass.getMethods(true);
        for (int i = 0; i < methods.length; i++)
        {
            if (methods[i].isPublic()
                && !methods[i].isStatic()
                && !methods[i].isPropertyAccessor()
                && !methods[i].isPropertyMutator()
                && !methods[i].isConstructor()
                && !OBJECT_CLASS_NAME.equals(methods[i].getParentClass().getName()))
            {
                this.operations.add(new QDoxMethodAdapter(methods[i]));
            }
        }

    }

    public void accept(JavaVisitor<org.mule.tools.cc.model.JavaClass> visitor)
    {
        visitor.visit(this);
    }
}
