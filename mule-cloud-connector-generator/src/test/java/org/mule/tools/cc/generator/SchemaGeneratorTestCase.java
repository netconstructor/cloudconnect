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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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

    @Test(expected = IllegalStateException.class)
    public void missingNamespaceIdentifierSuffix() throws Exception
    {
        generator.setNamespaceIdentifierSuffix(null);
        generator.generate(new ByteArrayOutputStream());
    }

    @Test(expected = IllegalStateException.class)
    public void missingSchemaVersion() throws Exception
    {
        generator.setSchemaVersion(null);
        generator.generate(new ByteArrayOutputStream());
    }

    @Test(expected = IllegalStateException.class)
    public void missingJavaClass() throws Exception
    {
        generator.setJavaClass(null);
        generator.generate(new ByteArrayOutputStream());
    }

    @Test
    public void singleArgumentOperation() throws Exception
    {
        MockJavaMethod javaMethod = createOperationMethod();
        MockJavaClass javaClass = new MockJavaClass(javaMethod);

        generator.setJavaClass(javaClass);
        generateAndCompareTo("single-argument-operation.xsd");
    }

    @Test
    public void settersBecomeConfigElement() throws Exception
    {
        MockJavaMethodParameter parameter = new MockJavaMethodParameter("value", "String");
        MockJavaMethod setter = new MockJavaMethod("setApiKey", "This key is required to use the API.", parameter);
        MockJavaMethod operation = createOperationMethod();
        MockJavaClass mockClass = new MockJavaClass(setter, operation);
        
        generator.setJavaClass(mockClass);
        generateAndCompareTo("generated-config-element.xsd");
    }
    
    @Test
    public void settersWithMoreThanOneArgumentAreIgnoredForConfigElement() throws Exception
    {
        MockJavaMethodParameter param1 = new MockJavaMethodParameter("valueOne", "String");
        MockJavaMethodParameter param2 = new MockJavaMethodParameter("valueTwo", "String");
        MockJavaMethod setter = new MockJavaMethod("setMustBeIgnored", null, param1, param2);
        MockJavaMethod operation = createOperationMethod();
        MockJavaClass mockClass = new MockJavaClass(setter, operation);
        
        generator.setJavaClass(mockClass);
        generateAndCompareTo("single-argument-operation.xsd");
    }

    @Test
    public void onlyPublicMethodsAreGenerated() throws Exception
    {
        MockJavaMethod protectedMethod = new MockJavaMethod("protectedMethod", null, false);
        MockJavaMethod operation = createOperationMethod();
        MockJavaClass mockClass = new MockJavaClass(protectedMethod, operation);

        generator.setJavaClass(mockClass);
        generateAndCompareTo("single-argument-operation.xsd");
    }

    @Test
    public void integerObjectArgument() throws Exception
    {
        MockJavaMethodParameter parameter = new MockJavaMethodParameter("anInt", "Integer");
        MockJavaMethod method = new MockJavaMethod("intConsumingMethod", null, parameter);
        MockJavaClass mockClass = new MockJavaClass(method);

        generator.setJavaClass(mockClass);
        generateAndCompareTo("integer-argument.xsd");
    }

    @Test
    public void integerNativeTypeArgument() throws Exception
    {
        MockJavaMethodParameter parameter = new MockJavaMethodParameter("anInt", "int");
        MockJavaMethod method = new MockJavaMethod("intConsumingMethod", null, parameter);
        MockJavaClass mockClass = new MockJavaClass(method);

        generator.setJavaClass(mockClass);
        generateAndCompareTo("integer-argument.xsd");
    }

    @Test
    public void booleanObjectArgument() throws Exception
    {
        MockJavaMethodParameter parameter = new MockJavaMethodParameter("aBool", "Boolean");
        MockJavaMethod method = new MockJavaMethod("boolConsumingMethod", null, parameter);
        MockJavaClass mockClass = new MockJavaClass(method);

        generator.setJavaClass(mockClass);
        generateAndCompareTo("boolean-argument.xsd");
    }

    @Test
    public void booleanNativeTypeArgument() throws Exception
    {
        MockJavaMethodParameter parameter = new MockJavaMethodParameter("aBool", "boolean");
        MockJavaMethod method = new MockJavaMethod("boolConsumingMethod", null, parameter);
        MockJavaClass mockClass = new MockJavaClass(method);

        generator.setJavaClass(mockClass);
        generateAndCompareTo("boolean-argument.xsd");
    }

    @Ignore
    @Test
    public void optionalArgument()
    {
        fail("implement me");
    }

    private MockJavaMethod createOperationMethod()
    {
        MockJavaMethodParameter parameter = new MockJavaMethodParameter("argument1", "String");
        MockJavaMethod javaMethod = new MockJavaMethod("operation",
            "This is the javadoc of the operation method", parameter);
        return javaMethod;
    }

    private void generateAndCompareTo(String filename) throws IOException
    {
        UnitTestUtils.runGeneratorAndCompareTo(generator, filename, printGeneratedSchema);
    }
}
