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

import org.mule.tools.cloudconnect.model.AbstractJavaParameter;
import org.mule.tools.cloudconnect.model.JavaAnnotation;
import org.mule.tools.cloudconnect.model.JavaMethod;
import org.mule.tools.cloudconnect.model.JavaType;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DocletTag;

import java.util.ArrayList;
import java.util.List;

public class QDoxParameterAdapter extends AbstractJavaParameter
{

    private com.thoughtworks.qdox.model.JavaParameter javaParameter;
    private JavaMethod parent;
    private List<JavaAnnotation> annotations;

    public QDoxParameterAdapter(JavaMethod parent, com.thoughtworks.qdox.model.JavaParameter javaParameter)
    {
        this.javaParameter = javaParameter;
        this.parent = parent;
    }

    public String getName()
    {
        return javaParameter.getName();
    }

    public JavaType getType()
    {
        return new QDoxTypeAdapter(this.javaParameter.getType());
    }

    public String getDescription()
    {
        if (javaParameter.getParentMethod() != null)
        {
            DocletTag[] doclets = javaParameter.getParentMethod().getTagsByName("param");

            for (int i = 0; i < doclets.length; i++)
            {
                if (doclets[i].getParameters()[0].equals(javaParameter.getName()))
                {
                    if (doclets[i].getValue().indexOf(' ') != -1)
                    {
                        return doclets[i].getValue().substring(doclets[i].getValue().indexOf(' '));
                    }
                }
            }
        }

        return null;
    }

    public JavaMethod getParentMethod()
    {
        return parent;
    }

    public void buildAnnotationCollection()
    {
        this.annotations = new ArrayList<JavaAnnotation>();

        if (javaParameter != null)
        {
            Annotation[] annotations = javaParameter.getAnnotations();
            for (int i = 0; i < annotations.length; i++)
            {
                this.annotations.add(new QDoxAnnotationAdapter(annotations[i]));
            }
        }
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
