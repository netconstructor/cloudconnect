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

import org.mule.tools.cloudconnect.model.AbstractJavaField;
import org.mule.tools.cloudconnect.model.JavaAnnotation;
import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaType;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaField;

import java.util.ArrayList;
import java.util.List;

public class QDoxFieldAdapter extends AbstractJavaField
{
    private JavaField javaField;
    private List<JavaAnnotation> annotations;

    public QDoxFieldAdapter(JavaField field)
    {
        this.javaField = field;
    }

    public void buildAnnotationCollection()
    {
        this.annotations = new ArrayList<JavaAnnotation>();

        Annotation[] annotations = javaField.getAnnotations();
        for (int i = 0; i < annotations.length; i++)
        {
            this.annotations.add(new QDoxAnnotationAdapter(annotations[i]));
        }
    }

    public String getName()
    {
        return this.javaField.getName();
    }

    public boolean isTransient()
    {
        return this.javaField.isTransient();
    }

    public boolean isPublic()
    {
        return this.javaField.isPublic();
    }

    public boolean isStatic()
    {
        return this.javaField.isStatic();
    }

    public JavaType getType()
    {
        return new QDoxTypeAdapter(this.javaField.getType());
    }

    public JavaClass getParentClass()
    {
        return new QDoxClassAdapter(this.javaField.getParentClass(), null);
    }

    public List<JavaAnnotation> getAnnotations()
    {
        if (annotations == null)
        {
            buildAnnotationCollection();
        }

        return annotations;
    }
}
