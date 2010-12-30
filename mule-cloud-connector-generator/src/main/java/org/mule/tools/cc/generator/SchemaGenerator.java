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

public class SchemaGenerator
{
    private BufferedWriter writer;

    public void generate(OutputStream output) throws IOException
    {
        createWriter(output);

        writeXmlPreamble();
        writeStartElement();
        writeImports();
        writeAnnotation();
        writeClosingStartElement();

        writer.flush();
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
        writer.write("<xsd:schema xmlns=\"http://www.mulesoft.org/schema/mule/test-schema\"");
        writer.newLine();
        writer.write("            xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
        writer.newLine();
        writer.write("            xmlns:mule=\"http://www.mulesoft.org/schema/mule/core\"");
        writer.newLine();
        writer.write("            xmlns:schemadoc=\"http://www.mulesoft.org/schema/mule/schemadoc\"");
        writer.newLine();
        writer.write("            xmlns:beans=\"http://www.springframework.org/schema/beans\"");
        writer.newLine();
        writer.write("            targetNamespace=\"http://www.mulesoft.org/schema/mule/test-schema\"");
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
        writer.write("                schemaLocation=\"http://www.mulesoft.org/schema/mule/core/3.1/mule.xsd\"/>");
        writer.newLine();
        writer.write("    <xsd:import namespace=\"http://www.mulesoft.org/schema/mule/schemadoc\"");
        writer.newLine();
        writer.write("                schemaLocation=\"http://www.mulesoft.org/schema/mule/schemadoc/3.1/mule-schemadoc.xsd\"/>");
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

    private void writeClosingStartElement() throws IOException
    {
        writer.write("</xsd:schema>");
        writer.newLine();
    }
}
