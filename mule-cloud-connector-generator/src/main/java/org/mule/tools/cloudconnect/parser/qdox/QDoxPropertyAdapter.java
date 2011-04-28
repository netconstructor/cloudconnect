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

import org.mule.tools.cloudconnect.model.AbstractJavaProperty;
import org.mule.tools.cloudconnect.model.JavaAnnotation;
import org.mule.tools.cloudconnect.model.JavaType;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaField;

import java.util.ArrayList;
import java.util.List;

public class QDoxPropertyAdapter extends AbstractJavaProperty
{

    private BeanProperty javaProperty;
    private JavaField javaField;
    private List<JavaAnnotation> annotations;


    protected QDoxPropertyAdapter(BeanProperty javaProperty, JavaField javaField)
    {
        this.javaProperty = javaProperty;
        this.javaField = javaField;
    }

    public String getName()
    {
        return this.javaProperty.getName();
    }

    public JavaType getType()
    {
        return new QDoxTypeAdapter(this.javaProperty.getType());
    }

    public org.mule.tools.cloudconnect.model.JavaField getField()
    {
        return new QDoxFieldAdapter(this.javaField);
    }

    public String getDescription()
    {
        if (this.javaProperty.getMutator() != null)
        {
            if (this.javaProperty.getMutator().getComment() != null)
            {
                return this.javaProperty.getMutator().getComment();
            }
            else if( this.javaField != null )
            {
                if (this.javaField.getComment() != null)
                {
                    return this.javaField.getComment();
                }
            }
        }

        return null;
    }

    public List<JavaAnnotation> getAnnotations()
    {
        if (annotations == null)
        {
            buildAnnotationCollection();
        }

        return annotations;
    }

    public String getAccessorName()
    {
        return this.javaProperty.getAccessor().getName();
    }

    public String getMutatorName()
    {
        return this.javaProperty.getMutator().getName();
    }

    public void buildAnnotationCollection()
    {
        this.annotations = new ArrayList<JavaAnnotation>();

        if (javaField != null)
        {
            Annotation[] annotations = javaField.getAnnotations();
            for (int i = 0; i < annotations.length; i++)
            {
                this.annotations.add(new QDoxAnnotationAdapter(annotations[i]));
            }
        }
    }
}
