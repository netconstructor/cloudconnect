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
import org.mule.tools.cloudconnect.model.JavaParameter;
import org.mule.tools.cloudconnect.model.JavaType;

import com.sun.xml.internal.bind.v2.model.nav.Navigator;
import com.sun.xml.internal.bind.v2.runtime.Location;

import java.util.Collection;
import java.util.List;

public class ModelNavigator implements Navigator<JavaType, JavaClass, JavaField, JavaMethod>
{

    private JavaModel javaModel;

    public ModelNavigator(JavaModel model)
    {
        this.javaModel = model;
    }

    public JavaClass getSuperClass(JavaClass javaClass)
    {
        return javaClass.getSuperClass();
    }

    public JavaType getBaseClass(JavaType javaType, JavaClass javaClass)
    {
        return null;
        //return javaClass.getSuperClass();
    }

    public String getClassName(JavaClass javaClass)
    {
        return javaClass.getName();
    }

    public String getTypeName(JavaType javaType)
    {
        return javaType.getName();
    }

    public String getClassShortName(JavaClass javaClass)
    {
        return javaClass.getName();
    }

    public Collection<? extends JavaField> getDeclaredFields(JavaClass javaClass)
    {
        return javaClass.getFields();
    }

    public JavaField getDeclaredField(JavaClass javaClass, String s)
    {
        for (JavaField field : javaClass.getFields())
        {
            if (field.getName().equals(s))
            {
                return field;
            }
        }

        return null;
    }

    public Collection<? extends JavaMethod> getDeclaredMethods(JavaClass javaClass)
    {
        return javaClass.getMethods();
    }

    public JavaClass getDeclaringClassForField(JavaField javaField)
    {
        return javaField.getParentClass();
    }

    public JavaClass getDeclaringClassForMethod(JavaMethod javaMethod)
    {
        return javaMethod.getParentClass();
    }

    public JavaType getFieldType(JavaField javaField)
    {
        return javaField.getType();
    }

    public String getFieldName(JavaField javaField)
    {
        return javaField.getName();
    }

    public String getMethodName(JavaMethod javaMethod)
    {
        return javaMethod.getName();
    }

    public JavaType getReturnType(JavaMethod javaMethod)
    {
        return javaMethod.getReturnType();
    }

    public JavaType[] getMethodParameters(JavaMethod javaMethod)
    {
        return (JavaType[]) javaMethod.getParameters().toArray();
    }

    public boolean isStaticMethod(JavaMethod javaMethod)
    {
        return javaMethod.isStatic();
    }

    public boolean isSubClassOf(JavaType javaType, JavaType javaType1)
    {
        return javaType1.isA(javaType);
    }

    public JavaType ref(Class aClass)
    {
        return javaModel.getType(aClass);
    }

    public JavaType use(JavaClass javaClass)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JavaClass asDecl(JavaType javaType)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JavaClass asDecl(Class aClass)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isArray(JavaType javaType)
    {
        return javaType.isArray();
    }

    public boolean isArrayButNotByteArray(JavaType javaType)
    {
        return (javaType.isArray() && !javaType.getName().equals("byte"));
    }

    public JavaType getComponentType(JavaType javaType)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JavaType getTypeArgument(JavaType javaType, int i)
    {
        return javaType.getTypeArguments().get(i);
    }

    public boolean isParameterizedType(JavaType javaType)
    {
        return javaType.isGeneric();
    }

    public boolean isPrimitive(JavaType javaType)
    {
        return javaType.isPrimitive();
    }

    public JavaType getPrimitive(Class aClass)
    {
        return javaModel.getType(aClass);
    }

    /*
     * THIS LOCATION METHODS ARE NOT NEEDED
     */
    public Location getClassLocation(JavaClass javaClass)
    {
        return null;
    }

    public Location getFieldLocation(JavaField javaField)
    {
        return null;
    }

    public Location getMethodLocation(JavaMethod javaMethod)
    {
        return null;
    }

    public boolean hasDefaultConstructor(JavaClass javaClass)
    {
        return javaClass.hasDefaultConstructor();
    }

    public boolean isStaticField(JavaField javaField)
    {
        return javaField.isStatic();
    }

    public boolean isPublicMethod(JavaMethod javaMethod)
    {
        return javaMethod.isPublic();
    }

    public boolean isPublicField(JavaField javaField)
    {
        return javaField.isPublic();
    }

    public boolean isEnum(JavaClass javaClass)
    {
        return javaClass.isEnum();
    }

    public <P> JavaType erasure(JavaType javaType)
    {
        return javaType.erasure();
    }

    public boolean isAbstract(JavaClass javaClass)
    {
        return javaClass.isAbstract();
    }

    public boolean isFinal(JavaClass javaClass)
    {
        return javaClass.isFinal();
    }

    public JavaField[] getEnumConstants(JavaClass javaClass)
    {
        return new JavaField[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JavaType getVoidType()
    {
        return this.javaModel.getVoidType();
    }

    public String getPackageName(JavaClass javaClass)
    {
        return javaClass.getPackage();
    }

    public JavaClass findClass(String s, JavaClass javaClass)
    {
        return javaModel.getClass(s);
    }

    public boolean isBridgeMethod(JavaMethod javaMethod)
    {
        return javaMethod.isVolatile();
    }

    public boolean isOverriding(JavaMethod javaMethod, JavaClass javaClass)
    {
        JavaClass base = javaClass;
        String name = javaMethod.getName();
        List<JavaParameter> parameters = javaMethod.getParameters();

        while (true)
        {

            if (base.getMethodBySignature(name, parameters) != null)
            {
                return true;
            }

            if (base.getSuperClass() == null)
            {
                return false;
            }

            base = base.getSuperClass();
        }

    }

    public boolean isInterface(JavaClass javaClass)
    {
        return javaClass.isInterface();
    }

    public boolean isTransient(JavaField javaField)
    {
        return javaField.isTransient();
    }

    public boolean isInnerClass(JavaClass javaClass)
    {
        return javaClass.isInnerClass();
    }
}
