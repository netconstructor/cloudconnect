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

package org.mule.tools.cloudconnect.model.jaxb;

import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaField;
import org.mule.tools.cloudconnect.model.JavaMethod;
import org.mule.tools.cloudconnect.model.JavaModel;
import org.mule.tools.cloudconnect.model.JavaType;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.tools.internal.xjc.api.J2SJAXBModel;
import com.sun.tools.internal.xjc.api.JavaCompiler;
import com.sun.xml.internal.bind.v2.model.core.Ref;
import com.sun.xml.internal.bind.v2.model.core.TypeInfoSet;
import com.sun.xml.internal.bind.v2.model.impl.ModelBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

public class ModelCompiler
{

    private JavaModel model;

    public ModelCompiler(JavaModel model)
    {
        this.model = model;
    }

    public JAXBModel bind(Collection<Reference> rootClasses, Map<QName, Reference> additionalElementDecls, String defaultNamespaceRemap, AnnotationProcessorEnvironment annotationProcessorEnvironment)
    {
        ModelBuilder<JavaType, JavaClass, JavaField, JavaMethod> builder =
                new ModelBuilder<JavaType, JavaClass, JavaField, JavaMethod>(
                        new ModelAnnotationReader(),
                        new ModelNavigator(model),
                        Collections.<JavaClass, JavaClass>emptyMap(),
                        defaultNamespaceRemap);

        //builder.setErrorHandler(new ErrorHandlerImpl(env.getMessager()));

        for (Reference ref : rootClasses)
        {
            JavaType t = ref.type;

            XmlJavaTypeAdapter xjta = ref.annotations.getAnnotation(XmlJavaTypeAdapter.class);
            XmlList xl = ref.annotations.getAnnotation(XmlList.class);

            builder.getTypeInfo(new Ref<JavaType, JavaClass>(builder, t, xjta, xl));
        }

        TypeInfoSet r = builder.link();
        if (r == null)
        {
            return null;
        }

        if (additionalElementDecls == null)
        {
            additionalElementDecls = Collections.emptyMap();
        }
        else
        {
            // fool proof check
            for (Map.Entry<QName, ? extends Reference> e : additionalElementDecls.entrySet())
            {
                if (e.getKey() == null)
                {
                    throw new IllegalArgumentException("nulls in additionalElementDecls");
                }
            }
        }
        return new JAXBModel(r, builder.reader, rootClasses, new HashMap(additionalElementDecls));
    }
}
