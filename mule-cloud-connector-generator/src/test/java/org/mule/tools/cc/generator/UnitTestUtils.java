/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cc.generator;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitTestUtils
{
    public static InputStream getTestResource(String filename)
    {
        InputStream input = UnitTestUtils.class.getClassLoader().getResourceAsStream(filename);
        assertNotNull(input);
        return input;
    }

    public static void runGeneratorAndCompareTo(AbstractTemplateGenerator templateGenerator,
                                                String filename,
                                                boolean printGeneratedOutput) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream(1500);
        templateGenerator.generate(output);

        if (printGeneratedOutput)
        {
            System.out.println(output);
        }

        InputStream sourceInput = new ByteArrayInputStream(output.toByteArray());
        InputStream controlInput = UnitTestUtils.getTestResource(filename);
        new LineByLineComparator(sourceInput, controlInput).compare();
    }

    public static JavaClass createMockClass(JavaMethod method)
    {
        JavaClass javaClass = mock(JavaClass.class);
        when(javaClass.getMethods()).thenReturn(new JavaMethod[]{ method });
        return javaClass;
    }

    public static JavaClass createMockClass(JavaMethod[] methods)
    {
        JavaClass javaClass = mock(JavaClass.class);
        when(javaClass.getMethods()).thenReturn(methods);
        return javaClass;
    }

    public static JavaClass createMockClass(String pkg, String name, JavaMethod[] methods)
    {
        JavaClass javaClass = mock(JavaClass.class);
        when(javaClass.getPackage()).thenReturn(pkg);
        when(javaClass.getName()).thenReturn(name);
        when(javaClass.getMethods()).thenReturn(methods);
        return javaClass;
    }

    public static JavaMethod createMockMethod(String name, String comment, JavaParameter parameter)
    {
        return createMockMethod(name, comment, new JavaParameter[]{ parameter });
    }

    public static JavaMethod createMockMethod(String name, String comment, JavaParameter[] parameters)
    {
        return createMockMethod(name, comment, parameters, true);
    }

    public static JavaMethod createMockMethod(String name,
                                              String comment,
                                              JavaParameter[] parameters,
                                              boolean pub)
    {
        JavaMethod method = mock(JavaMethod.class);
        when(method.getName()).thenReturn(name);
        when(method.getComment()).thenReturn(comment);
        when(method.getParameters()).thenReturn(parameters);
        when(method.isPublic()).thenReturn(pub);
        return method;
    }

    public static JavaParameter createMockParameter(String symbol, String typeName)
    {
        JavaParameter parameter = mock(JavaParameter.class);
        when(parameter.getName()).thenReturn(symbol);
        Type type = mock(Type.class);
        when(type.getValue()).thenReturn(typeName);
        when(parameter.getType()).thenReturn(type);
        return parameter;
    }

    private UnitTestUtils()
    {
        // do not instantiate
    }
}
