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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import org.apache.commons.lang.StringUtils;

public class NamespaceHandlerGenerator extends AbstractGenerator
{
    private String packageName;
    private String className;

    @Override
    public void generate(OutputStream output) throws IOException
    {
        checkAllRequiredFieldsSet();
        writer = new GeneratorWriter(output);

        generateFileHeader();
        generatePackageDeclaration();
        generateImports();
        generateClassDeclaration();
        generateInitMethod();
        generateClassFooter();

        writer.flush();
    }

    @Override
    protected void checkAllRequiredFieldsSet()
    {
        super.checkAllRequiredFieldsSet();

        if (StringUtils.isEmpty(packageName))
        {
            throw new IllegalStateException("packageName is not set");
        }
        if (StringUtils.isEmpty(className))
        {
            throw new IllegalStateException("className is not set");
        }
    }

    private void generateFileHeader() throws IOException
    {
        writer.writeLine("//");
        writer.writeLine("// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!");
        writer.writeLine("//");
    }

    private void generatePackageDeclaration() throws IOException
    {
        writer.newLine();
        writer.write("package ");
        writer.write(packageName);
        writer.writeLine(";");
    }

    private void generateImports() throws IOException
    {
        writer.newLine();
        writer.writeLine("import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;");
        writer.writeLine("import org.mule.config.spring.parsers.specific.InvokerMessageProcessorDefinitionParser;");

        writer.write("import ");
        writer.write(javaClass.getPackage());
        writer.write(".");
        writer.write(javaClass.getName());
        writer.writeLine(";");
    }

    private void generateClassDeclaration() throws IOException
    {
        writer.newLine();
        writer.write("public class ");
        writer.write(className);
        writer.writeLine(" extends AbstractPojoNamespaceHandler");
        writer.writeLine("{");
    }

    private void generateInitMethod() throws IOException
    {
        writer.indentDepth(4);
        writer.writeLine("public void init()");
        writer.writeLine("{");

        generateConfigPojoRegistrationIfNecessary();

        writer.writeLine("    InvokerMessageProcessorDefinitionParser parser = null;");
        
        writer.indentDepth(8);
        for (JavaMethod method : javaClass.getMethods())
        {
            if (JavaClassUtils.isSetterMethod(method) == false)
            {
                generateParserForMethod(method);
            }
        }
        writer.indentDepth(4);

        writer.writeLine("}");
        writer.resetIndentDepth();
    }

    private void generateConfigPojoRegistrationIfNecessary() throws IOException
    {
        List<JavaMethod> setterMethods = JavaClassUtils.collectSetters(javaClass);
        if (setterMethods.size() > 0)
        {
            writer.writeLine("    registerPojo(\"config\", %1s.class);", javaClass.getName());
            writer.newLine();
        }
    }

    private void generateParserForMethod(JavaMethod method) throws IOException
    {        
        writer.newLine();
        writer.writeLine("parser = new InvokerMessageProcessorDefinitionParser(\"messageProcessor\",");
        writer.write("            ");
        writer.write(javaClass.getName());
        writer.write(".class, \"");
        writer.write(method.getName());
        writer.write("\", new String[] { ");

        JavaParameter[] parameters = method.getParameters();
        for( int i = 0; i < parameters.length; i++ )
        {
            JavaParameter parameter = parameters[i];

            writer.write("\"");
            writer.write(parameter.getName());
            writer.write("\"");

            if (i != parameters.length -1 )
            {
                writer.write(", ");
            }
        }

        writer.write(" }");
        writer.write(");");
        writer.newLine();

        String elementName = SchemaGenerator.splitCamelCase(method.getName());
        writer.writeLine("registerMuleBeanDefinitionParser(\"%1s\", parser);", elementName);
    }

    private void generateClassFooter() throws IOException
    {
        writer.writeLine("}");
    }

    public void setPackageName(String name)
    {
        packageName = name;
    }

    public void setClassName(String name)
    {
        className = name;
    }
}
