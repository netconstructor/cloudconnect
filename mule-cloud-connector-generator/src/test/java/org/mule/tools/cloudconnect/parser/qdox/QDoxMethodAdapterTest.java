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

import org.mule.tools.cloudconnect.model.JavaClass;

import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class QDoxMethodAdapterTest
{

    private static final String NAME = "NAME";
    private static final String COMMENT = "COMMENT";
    private static final String PARAMETER_A = "PARAMETER_A";
    private static final String PARAMETER_B = "PARAMETER_B";
    private static final String PARAMETER_C = "PARAMETER_C";

    @Test
    public void name() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(NAME);

        QDoxMethodAdapter adapter = new QDoxMethodAdapter(methodMock, classMock);

        assertEquals(NAME, adapter.getName());
    }

    @Test
    public void description() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getComment()).thenReturn(COMMENT);

        QDoxMethodAdapter adapter = new QDoxMethodAdapter(methodMock, classMock);

        assertEquals(COMMENT, adapter.getDescription());

    }

    @Test
    public void parameters() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        JavaMethod methodMock = mock(JavaMethod.class);
        JavaParameter parameterMockA = mock(JavaParameter.class);
        when(parameterMockA.getName()).thenReturn(PARAMETER_A);
        JavaParameter parameterMockB = mock(JavaParameter.class);
        when(parameterMockB.getName()).thenReturn(PARAMETER_B);
        JavaParameter parameterMockC = mock(JavaParameter.class);
        when(parameterMockC.getName()).thenReturn(PARAMETER_C);
        JavaParameter[] parameters = new JavaParameter[] {parameterMockA, parameterMockB, parameterMockC};
        when(methodMock.getParameters()).thenReturn(parameters);

        QDoxMethodAdapter adapter = new QDoxMethodAdapter(methodMock, classMock);

        assertEquals(3, adapter.getParameters().size());
        assertEquals(PARAMETER_A, adapter.getParameters().get(0).getName());
        assertEquals(PARAMETER_B, adapter.getParameters().get(1).getName());
        assertEquals(PARAMETER_C, adapter.getParameters().get(2).getName());

    }
}
