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

import org.mule.tools.cloudconnect.parser.ClassParseException;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.parser.ParseException;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QDoxClassParserTest
{

    private static final String CLASS_A = "CLASS_A";

    @Test(expected = ClassParseException.class)
    public void noClasses() throws Exception
    {
        File fileMock = mock(File.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);
        when(javaDocBuilder.getClasses()).thenReturn(null);

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        parser.addSourceTree(fileMock);
        parser.parse();
    }

    @Test(expected = ClassParseException.class)
    public void parseException() throws Exception
    {
        File fileMock = mock(File.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);
        when(javaDocBuilder.getClasses()).thenThrow(new ParseException("", 0, 0));

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        parser.addSourceTree(fileMock);
        parser.parse();
    }


    @Test
    public void success() throws Exception
    {
        File fileMock = mock(File.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);

        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getName()).thenReturn(CLASS_A);
        when(classMock.getBeanProperties(eq(true))).thenReturn(new BeanProperty[] {});
        when(classMock.getMethods(eq(true))).thenReturn(new JavaMethod[] {});
        JavaClass[] classes = new JavaClass[] {classMock};
        when(javaDocBuilder.getClasses()).thenReturn(classes);

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        parser.addSourceTree(fileMock);
        org.mule.tools.cloudconnect.model.JavaClass result = parser.parse().getClasses().get(0);

        assertEquals(CLASS_A, result.getName());
    }
}
