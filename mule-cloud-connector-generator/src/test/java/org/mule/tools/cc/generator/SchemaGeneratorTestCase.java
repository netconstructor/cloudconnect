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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

public class SchemaGeneratorTestCase
{
    private SchemaGenerator generator;
    private boolean printGeneratedSchema = false;

    @Before
    public void createSchemaGenerator()
    {
        generator = new SchemaGenerator();
        generator.setNamespaceIdentifierSuffix("test-schema");
        generator.setSchemaVersion("3.1");
    }

    @Test
    public void singleArgumentOperation() throws Exception
    {
        JavaMethod javaMethod = createOperationMethod();
        JavaClass javaClass = UnitTestUtils.createMockClass(javaMethod);

        generator.setJavaClass(javaClass);

        generateAndCompareTo("single-argument-operation.xsd");
    }

    @Test
    public void settersBecomeConfigElement() throws Exception
    {
        JavaParameter parameter = UnitTestUtils.createMockParameter("value", "java.lang.String");
        JavaMethod setter = UnitTestUtils.createMockMethod("setApiKey", "This key is required to use the API.", new JavaParameter[] { parameter });
        JavaMethod operation = createOperationMethod();
        JavaClass mockClass = UnitTestUtils.createMockClass("pkg", "class", new JavaMethod[] { setter, operation});
        
        generator.setJavaClass(mockClass);

        generateAndCompareTo("generated-config-element.xsd");
    }
    
    @Test
    public void settersWithMoreThanOneArgumentAreIgnoredForConfigElement() throws Exception
    {
        JavaParameter param1 = UnitTestUtils.createMockParameter("valueOne", "java.lang.String");
        JavaParameter param2 = UnitTestUtils.createMockParameter("valueTwo", "java.lang.String");
        JavaMethod setter = UnitTestUtils.createMockMethod("setMustBeIgnored", null, new JavaParameter[]{ param1, param2 });
        JavaMethod operation = createOperationMethod();
        JavaClass mockClass = UnitTestUtils.createMockClass(new JavaMethod[]{ setter, operation });

        generator.setJavaClass(mockClass);

        generateAndCompareTo("invalid-setter.xsd");
    }

    @Test
    public void onlyPublicMethodsAreGenerated() throws Exception
    {
        JavaMethod protectedMethod = UnitTestUtils.createMockMethod("protectedMethod", "", new JavaParameter[] {}, false);
        JavaMethod operation = createOperationMethod();
        JavaClass mockClass = UnitTestUtils.createMockClass( new JavaMethod[]{ protectedMethod, operation });

        generator.setJavaClass(mockClass);

        generateAndCompareTo("single-argument-operation.xsd");
    }

    @Test
    public void integerObjectArgument() throws Exception
    {
        JavaParameter parameter = UnitTestUtils.createMockParameter("anInt", "java.lang.Integer");
        JavaMethod method = UnitTestUtils.createMockMethod("intConsumingMethod", null, parameter);
        JavaClass mockClass = UnitTestUtils.createMockClass(method);

        generator.setJavaClass(mockClass);

        generateAndCompareTo("integer-argument.xsd");
    }

    @Test
    public void integerNativeTypeArgument() throws Exception
    {
        JavaParameter parameter = UnitTestUtils.createMockParameter("anInt", "int");
        JavaMethod method = UnitTestUtils.createMockMethod("intConsumingMethod", null, parameter);
        JavaClass mockClass = UnitTestUtils.createMockClass(method);

        generator.setJavaClass(mockClass);

        generateAndCompareTo("integer-argument.xsd");
    }

    @Test
    public void booleanObjectArgument() throws Exception
    {
        JavaParameter parameter = UnitTestUtils.createMockParameter("aBool", "java.lang.Boolean");
        JavaMethod method = UnitTestUtils.createMockMethod("boolConsumingMethod", null, parameter);
        JavaClass mockClass = UnitTestUtils.createMockClass(method);

        generator.setJavaClass(mockClass);

        generateAndCompareTo("boolean-argument.xsd");
    }

    @Test
    public void booleanNativeTypeArgument() throws Exception
    {
        JavaParameter parameter = UnitTestUtils.createMockParameter("aBool", "boolean");
        JavaMethod method = UnitTestUtils.createMockMethod("boolConsumingMethod", "", parameter);
        JavaClass mockClass = UnitTestUtils.createMockClass(method);

        generator.setJavaClass(mockClass);

        generateAndCompareTo("boolean-argument.xsd");
    }

    @Test
    public void dateObjectArgument() throws Exception
    {
        JavaParameter parameter = UnitTestUtils.createMockParameter("aDate", "java.lang.Date");
        JavaMethod method = UnitTestUtils.createMockMethod("dateConsumingMethod", "", parameter);
        JavaClass mockClass = UnitTestUtils.createMockClass(method);

        generator.setJavaClass(mockClass);

        generateAndCompareTo("date-argument.xsd");
    }

    @Ignore
    @Test
    public void optionalArgument()
    {
        fail("implement me");
    }

    private JavaMethod createOperationMethod()
    {
        JavaParameter parameter = UnitTestUtils.createMockParameter("argument1", "java.lang.String");
        JavaMethod javaMethod = UnitTestUtils.createMockMethod("operation",
            "This is the javadoc of the operation method", new JavaParameter[]{ parameter });
        return javaMethod;
    }

    private void generateAndCompareTo(String filename) throws IOException
    {
        UnitTestUtils.runGeneratorAndCompareTo(generator, filename, printGeneratedSchema);
    }
}
