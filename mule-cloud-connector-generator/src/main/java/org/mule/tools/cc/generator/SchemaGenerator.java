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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.StringUtils;

public class SchemaGenerator
{
    private String namespaceIdentifierSuffix;
    private BufferedWriter writer;
    private String schemaVersion;
    private JavaClass javaClass;

    public void generate(OutputStream output) throws IOException
    {
        checkAllRequiredFieldsSet();
        createWriter(output);

        writeXmlPreamble();
        writeStartElement();
        writeImports();
        writeAnnotation();
        writeOperations();
        writeClosingStartElement();

        writer.flush();
    }

    private void checkAllRequiredFieldsSet()
    {
        if (StringUtils.isEmpty(namespaceIdentifierSuffix))
        {
            throw new IllegalStateException("namespaceIdentifierSuffix is not set");
        }
        if (StringUtils.isEmpty(schemaVersion))
        {
            throw new IllegalStateException("schemaVersion is not set");
        }
        if (javaClass == null)
        {
            throw new IllegalStateException("javaClass is not set");
        }
    }

    private void createWriter(OutputStream outputStream)
    {
        OutputStreamWriter osw = new OutputStreamWriter(outputStream);
        writer = new BufferedWriter(osw);
    }

    private void writeXmlPreamble() throws IOException
    {
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        writer.newLine();
    }

    private void writeStartElement() throws IOException
    {
        writer.write("<xsd:schema xmlns=\"http://www.mulesoft.org/schema/mule/");
        writer.write(namespaceIdentifierSuffix);
        writer.write("\"");
        writer.newLine();
        writer.write("            xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
        writer.newLine();
        writer.write("            xmlns:mule=\"http://www.mulesoft.org/schema/mule/core\"");
        writer.newLine();
        writer.write("            xmlns:schemadoc=\"http://www.mulesoft.org/schema/mule/schemadoc\"");
        writer.newLine();
        writer.write("            xmlns:beans=\"http://www.springframework.org/schema/beans\"");
        writer.newLine();
        writer.write("            targetNamespace=\"http://www.mulesoft.org/schema/mule/");
        writer.write(namespaceIdentifierSuffix);
        writer.write("\"");
        writer.newLine();
        writer.write("            elementFormDefault=\"qualified\"");
        writer.newLine();
        writer.write("            attributeFormDefault=\"unqualified\">");
        writer.newLine();
    }

    private void writeImports() throws IOException
    {
        writer.newLine();
        writer.write("    <xsd:import namespace=\"http://www.w3.org/XML/1998/namespace\"/>");
        writer.newLine();
        writer.write("    <xsd:import namespace=\"http://www.springframework.org/schema/beans\"");
        writer.newLine();
        writer.write("                schemaLocation=\"http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\"/>");
        writer.newLine();
        writer.write("    <xsd:import namespace=\"http://www.mulesoft.org/schema/mule/core\"");
        writer.newLine();
        writer.write("                schemaLocation=\"http://www.mulesoft.org/schema/mule/core/");
        writer.write(schemaVersion);
        writer.write("/mule.xsd\"/>");
        writer.newLine();
        writer.write("    <xsd:import namespace=\"http://www.mulesoft.org/schema/mule/schemadoc\"");
        writer.newLine();
        writer.write("                schemaLocation=\"http://www.mulesoft.org/schema/mule/schemadoc/");
        writer.write(schemaVersion);
        writer.write("/mule-schemadoc.xsd\"/>");
        writer.newLine();
    }

    private void writeAnnotation() throws IOException
    {
        writer.newLine();
        writer.write("    <xsd:annotation>");
        writer.newLine();
        writer.write("        <xsd:documentation>");
        writer.newLine();
        writer.write("            This schema was auto-generated. Do not edit.");
        writer.newLine();
        writer.write("        </xsd:documentation>");
        writer.newLine();
        writer.write("    </xsd:annotation>");
        writer.newLine();
    }

    private void writeOperations() throws IOException
    {
        writer.newLine();
        writer.write("    <!-- Operations -->");
        writer.newLine();

        for (JavaMethod method : javaClass.getMethods())
        {
            writeOperation(method);
        }
    }

    private void writeOperation(JavaMethod method) throws IOException
    {
        writeOperationElementDeclaration(method);
        writeOperationElementType(method);
    }

    private void writeOperationElementDeclaration(JavaMethod method) throws IOException
    {
        writer.write("    <xsd:element name=\"");
        writer.write(method.getName());
        writer.write("\" type=\"");
        writer.write(method.getName());
        writer.write("Type\" substitutionGroup=\"mule:abstract-message-processor\">");
        writer.newLine();

        if (StringUtils.isNotEmpty(method.getJavadoc()))
        {
            writeOperationDocumentation(method);
        }

        writer.write("    </xsd:element>");
        writer.newLine();
    }

    private void writeOperationDocumentation(JavaMethod method) throws IOException
    {
        writer.write("        <xsd:annotation>");
        writer.newLine();
        writer.write("            <xsd:documentation>");
        writer.newLine();
        writer.write("                ");
        writer.write(method.getJavadoc());
        writer.newLine();
        writer.write("            </xsd:documentation>");
        writer.newLine();
        writer.write("        </xsd:annotation>");
        writer.newLine();
    }

    private void writeOperationElementType(JavaMethod method) throws IOException
    {
        writer.write("    <xsd:complexType name=\"operationType\">");
        writer.newLine();
        writer.write("        <xsd:complexContent>");
        writer.newLine();
        writer.write("            <xsd:extension base=\"mule:abstractInterceptingMessageProcessorType\">");
        writer.newLine();

        for (JavaMethodParameter parameter : method.getParameters())
        {
            writeOperationParameterAttribute(parameter);
        }

        writer.write("            </xsd:extension>");
        writer.newLine();
        writer.write("        </xsd:complexContent>");
        writer.newLine();
        writer.write("    </xsd:complexType>");
        writer.newLine();
    }

    private void writeOperationParameterAttribute(JavaMethodParameter parameter) throws IOException
    {
        writer.write("                <xsd:attribute name=\"");
        writer.write(parameter.getName());
        writer.write("\" type=\"xsd:string\"/>");
        writer.newLine();
    }

    private void writeClosingStartElement() throws IOException
    {
        writer.write("</xsd:schema>");
        writer.newLine();
    }

    public void setNamespaceIdentifierSuffix(String suffix)
    {
        namespaceIdentifierSuffix = suffix;
    }

    public void setSchemaVersion(String version)
    {
        schemaVersion = version;
    }

    public void setJavaClass(JavaClass klass)
    {
        javaClass = klass;
    }
}
