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
import org.mule.tools.cloudconnect.model.JavaType;

import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
import com.sun.xml.internal.bind.v2.model.core.ErrorHandler;

import java.lang.annotation.Annotation;

public class ModelAnnotationReader implements AnnotationReader<JavaType, JavaClass, JavaField, JavaMethod>
{

    public void setErrorHandler(ErrorHandler errorHandler)
    {
        // DO NOTHING
    }

    public <A extends Annotation> A getFieldAnnotation(Class<A> aClass, JavaField javaField, Locatable locatable)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasFieldAnnotation(Class<? extends Annotation> aClass, JavaField javaField)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasClassAnnotation(JavaClass javaClass, Class<? extends Annotation> aClass)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Annotation[] getAllFieldAnnotations(JavaField javaField, Locatable locatable)
    {
        return new Annotation[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <A extends Annotation> A getMethodAnnotation(Class<A> aClass, JavaMethod javaMethod, JavaMethod javaMethod1, Locatable locatable)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasMethodAnnotation(Class<? extends Annotation> aClass, String s, JavaMethod javaMethod, JavaMethod javaMethod1, Locatable locatable)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Annotation[] getAllMethodAnnotations(JavaMethod javaMethod, Locatable locatable)
    {
        return new Annotation[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <A extends Annotation> A getMethodAnnotation(Class<A> aClass, JavaMethod javaMethod, Locatable locatable)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasMethodAnnotation(Class<? extends Annotation> aClass, JavaMethod javaMethod)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <A extends Annotation> A getMethodParameterAnnotation(Class<A> aClass, JavaMethod javaMethod, int i, Locatable locatable)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <A extends Annotation> A getClassAnnotation(Class<A> aClass, JavaClass javaClass, Locatable locatable)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <A extends Annotation> A getPackageAnnotation(Class<A> aClass, JavaClass javaClass, Locatable locatable)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JavaType getClassValue(Annotation annotation, String s)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JavaType[] getClassArrayValue(Annotation annotation, String s)
    {
        return new JavaType[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
