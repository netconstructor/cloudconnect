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

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

public class SchemaGeneratorTestCase
{
    private EasyMockHelper easyMockHelper;
    private SchemaGenerator generator;
    private boolean printGeneratedSchema = false;

    @Before
    public void createSchemaGenerator()
    {
        easyMockHelper = new EasyMockHelper();

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
        JavaMethod javaMethod = createOperationMethod();
        JavaClass javaClass = easyMockHelper.createMockClass(javaMethod);

        generator.setJavaClass(javaClass);

        easyMockHelper.replay();

        generateAndCompareTo("single-argument-operation.xsd");

        easyMockHelper.verify();
    }

    @Test
    public void settersBecomeConfigElement() throws Exception
    {
        JavaParameter parameter = easyMockHelper.createMockParameter("value", "String");
        JavaMethod setter = easyMockHelper.createMockMethod("setApiKey", "This key is required to use the API.", new JavaParameter[] { parameter });
        JavaMethod operation = createOperationMethod();
        JavaClass mockClass = easyMockHelper.createMockClass("pkg", "class", new JavaMethod[] { setter, operation});
        
        generator.setJavaClass(mockClass);

        easyMockHelper.replay();

        generateAndCompareTo("generated-config-element.xsd");

        easyMockHelper.verify();
    }
    
    @Test
    public void settersWithMoreThanOneArgumentAreIgnoredForConfigElement() throws Exception
    {
        JavaParameter param1 = easyMockHelper.createMockParameter("valueOne", "String");
        JavaParameter param2 = easyMockHelper.createMockParameter("valueTwo", "String");
        JavaMethod setter = easyMockHelper.createMockMethod("setMustBeIgnored", null, new JavaParameter[]{ param1, param2 });
        JavaMethod operation = createOperationMethod();
        JavaClass mockClass = easyMockHelper.createMockClass(new JavaMethod[]{ setter, operation });

        generator.setJavaClass(mockClass);

        easyMockHelper.replay();

        generateAndCompareTo("invalid-setter.xsd");

        easyMockHelper.verify();
    }

    @Test
    public void onlyPublicMethodsAreGenerated() throws Exception
    {
        JavaMethod protectedMethod = easyMockHelper.createMockMethod("protectedMethod", "", new JavaParameter[] {}, false);
        JavaMethod operation = createOperationMethod();
        JavaClass mockClass = easyMockHelper.createMockClass( new JavaMethod[]{ protectedMethod, operation });

        generator.setJavaClass(mockClass);

        easyMockHelper.replay();

        generateAndCompareTo("single-argument-operation.xsd");

        easyMockHelper.verify();
    }

    @Test
    public void integerObjectArgument() throws Exception
    {
        JavaParameter parameter = easyMockHelper.createMockParameter("anInt", "Integer");
        JavaMethod method = easyMockHelper.createMockMethod("intConsumingMethod", null, parameter);
        JavaClass mockClass = easyMockHelper.createMockClass(method);

        generator.setJavaClass(mockClass);

        easyMockHelper.replay();

        generateAndCompareTo("integer-argument.xsd");

        easyMockHelper.verify();
    }

    @Test
    public void integerNativeTypeArgument() throws Exception
    {
        JavaParameter parameter = easyMockHelper.createMockParameter("anInt", "int");
        JavaMethod method = easyMockHelper.createMockMethod("intConsumingMethod", null, parameter);
        JavaClass mockClass = easyMockHelper.createMockClass(method);

        generator.setJavaClass(mockClass);

        easyMockHelper.replay();

        generateAndCompareTo("integer-argument.xsd");

        easyMockHelper.verify();
    }

    @Test
    public void booleanObjectArgument() throws Exception
    {
        JavaParameter parameter = easyMockHelper.createMockParameter("aBool", "Boolean");
        JavaMethod method = easyMockHelper.createMockMethod("boolConsumingMethod", null, parameter);
        JavaClass mockClass = easyMockHelper.createMockClass(method);

        generator.setJavaClass(mockClass);

        easyMockHelper.replay();

        generateAndCompareTo("boolean-argument.xsd");

        easyMockHelper.verify();
    }

    @Test
    public void booleanNativeTypeArgument() throws Exception
    {
        JavaParameter parameter = easyMockHelper.createMockParameter("aBool", "boolean");
        JavaMethod method = easyMockHelper.createMockMethod("boolConsumingMethod", "", parameter);
        JavaClass mockClass = easyMockHelper.createMockClass(method);

        generator.setJavaClass(mockClass);

        easyMockHelper.replay();

        generateAndCompareTo("boolean-argument.xsd");

        easyMockHelper.verify();
    }

    @Ignore
    @Test
    public void optionalArgument()
    {
        fail("implement me");
    }

    private JavaMethod createOperationMethod()
    {
        JavaParameter parameter = easyMockHelper.createMockParameter("argument1", "String");
        JavaMethod javaMethod = easyMockHelper.createMockMethod("operation",
            "This is the javadoc of the operation method", new JavaParameter[]{ parameter });
        return javaMethod;
    }

    private void generateAndCompareTo(String filename) throws IOException
    {
        UnitTestUtils.runGeneratorAndCompareTo(generator, filename, printGeneratedSchema);
    }
}
