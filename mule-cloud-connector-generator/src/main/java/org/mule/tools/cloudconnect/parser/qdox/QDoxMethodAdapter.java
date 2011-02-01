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

import org.mule.tools.cloudconnect.model.AbstractJavaMethod;
import org.mule.tools.cloudconnect.model.JavaAnnotation;
import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaParameter;

import com.thoughtworks.qdox.model.Annotation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class QDoxMethodAdapter extends AbstractJavaMethod
{

    private com.thoughtworks.qdox.model.JavaMethod javaMethod;
    private WeakReference<JavaClass> javaClass;
    private List<JavaParameter> parameters;
    private List<JavaAnnotation> annotations;

    public QDoxMethodAdapter(com.thoughtworks.qdox.model.JavaMethod javaMethod, JavaClass owner)
    {
        this.javaMethod = javaMethod;
        this.javaClass = new WeakReference<JavaClass>(owner);
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

    public List<JavaAnnotation> getAnnotations()
    {
        if (annotations == null)
        {
            buildAnnotationCollection();
        }

        return annotations;
    }

    public boolean isPublic()
    {
        return javaMethod.isPublic();
    }

    public boolean isPropertyAccessor()
    {
        return javaMethod.isPropertyAccessor();
    }

    public boolean isPropertyMutator()
    {
        return javaMethod.isPropertyMutator();
    }

    public boolean isConstructor()
    {
        return javaMethod.isConstructor();
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

    public void buildAnnotationCollection()
    {
        this.annotations = new ArrayList<JavaAnnotation>();

        Annotation[] annotations = javaMethod.getAnnotations();
        for (int i = 0; i < annotations.length; i++)
        {
            this.annotations.add(new QDoxAnnotationAdapter(annotations[i]));
        }
    }

    public boolean isStatic()
    {
        return javaMethod.isStatic();
    }

    public JavaClass getParentClass()
    {
        return this.javaClass.get();
    }
}
