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

import org.mule.tools.cloudconnect.model.AbstractJavaMethod;
import org.mule.tools.cloudconnect.model.JavaAnnotation;
import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaParameter;
import org.mule.tools.cloudconnect.model.JavaType;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.Type;

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

    public JavaType getReturnType()
    {
        return new QDoxTypeAdapter(javaMethod.getReturnType());
    }
}
