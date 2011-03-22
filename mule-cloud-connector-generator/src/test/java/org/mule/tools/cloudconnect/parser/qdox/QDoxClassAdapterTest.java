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

import org.mule.tools.cloudconnect.model.JavaModel;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaPackage;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QDoxClassAdapterTest
{

    private static final String COMMENT = "COMMENT";
    private static final String PACKAGE = "PACKAGE";
    private static final String NAME = "NAME";
    private static final String PROPERTY_A = "PROPERTY_A";
    private static final String PROPERTY_B = "PROPERTY_B";
    private static final String PROPERTY_C = "PROPERTY_C";
    private static final String METHOD_A = "METHOD_A";
    private static final String CLASS_PROPERTY = "class";
    private static final String HASHCODE_METHOD_NAME = "hashCode";
    private static final String WAIT_METHOD_NAME = "wait";
    private static final String TOSTRING_METHOD_NAME = "toString";

    @Test
    public void name() throws Exception
    {
        JavaModel model = new QDoxModel();
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getName()).thenReturn(NAME);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] {});

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock, model);

        assertEquals(NAME, adapter.getName());
    }

    @Test
    public void pkg() throws Exception
    {
        JavaModel model = new QDoxModel();
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        JavaPackage packageMock = mock(JavaPackage.class);
        when(classMock.getPackage()).thenReturn(packageMock);
        when(packageMock.getName()).thenReturn(PACKAGE);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock, model);

        assertEquals(PACKAGE, adapter.getPackage());
    }

    @Test
    public void description() throws Exception
    {
        JavaModel model = new QDoxModel();
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getComment()).thenReturn(COMMENT);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock, model);

        assertEquals(COMMENT, adapter.getDescription());
    }

    @Test
    public void properties() throws Exception
    {
        JavaModel model = new QDoxModel();
        JavaClass typeClassMock = mock(JavaClass.class);
        when(typeClassMock.isEnum()).thenReturn(false);
        Type typeMock = mock(Type.class);
        when(typeMock.getJavaClass()).thenReturn(typeClassMock);
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] {});
        BeanProperty propertyMockA = mock(BeanProperty.class);
        when(propertyMockA.getName()).thenReturn(PROPERTY_A);
        when(propertyMockA.getType()).thenReturn(typeMock);
        BeanProperty propertyMockB = mock(BeanProperty.class);
        when(propertyMockB.getName()).thenReturn(PROPERTY_B);
        when(propertyMockB.getType()).thenReturn(typeMock);
        BeanProperty propertyMockC = mock(BeanProperty.class);
        when(propertyMockC.getName()).thenReturn(PROPERTY_C);
        when(propertyMockC.getType()).thenReturn(typeMock);
        BeanProperty[] properties = new BeanProperty[] {propertyMockA, propertyMockB, propertyMockC};
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock, model);

        assertEquals(3, adapter.getProperties().size());
        assertEquals(PROPERTY_A, adapter.getProperties().get(0).getName());
        assertEquals(PROPERTY_B, adapter.getProperties().get(1).getName());
        assertEquals(PROPERTY_C, adapter.getProperties().get(2).getName());
    }

    @Test
    public void classBeanPropertyNotAvailable() throws Exception
    {
        JavaModel model = new QDoxModel();
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        BeanProperty propertyMockA = mock(BeanProperty.class);
        when(propertyMockA.getName()).thenReturn(CLASS_PROPERTY);
        BeanProperty[] properties = new BeanProperty[] {propertyMockA};
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock, model);

        assertEquals(0, adapter.getProperties().size());
    }

    @Test
    public void hasProperties() throws Exception
    {
        JavaModel model = new QDoxModel();
        JavaClass typeClassMock = mock(JavaClass.class);
        when(typeClassMock.isEnum()).thenReturn(false);
        Type typeMock = mock(Type.class);
        when(typeMock.getJavaClass()).thenReturn(typeClassMock);
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        BeanProperty propertyMock = mock(BeanProperty.class);
        when(propertyMock.getType()).thenReturn(typeMock);
        BeanProperty[] properties = new BeanProperty[] {propertyMock};
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock, model);

        assertTrue(adapter.hasProperties());
    }

    @Test
    public void doesntHaveProperties() throws Exception
    {
        JavaModel model = new QDoxModel();
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        BeanProperty[] properties = new BeanProperty[] {};
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock, model);

        assertFalse(adapter.hasProperties());
    }

    @Test
    public void operations() throws Exception
    {
        JavaModel model = new QDoxModel();
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        when(classMock.getName()).thenReturn(NAME);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        when(methodMock.getParentClass()).thenReturn(classMock);
        when(methodMock.getParameters()).thenReturn(new JavaParameter[] { });
        JavaMethod[] methods = new JavaMethod[] {methodMock};
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock, model);

        assertEquals(1, adapter.getMethods().size());
        assertEquals(METHOD_A, adapter.getMethods().get(0).getName());
    }
}
