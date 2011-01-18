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
import java.util.List;

import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import org.apache.commons.lang.StringUtils;

public class SchemaGenerator extends AbstractGenerator
{
    private String namespaceIdentifierSuffix;
    private String schemaVersion;

    public static String splitCamelCase(String string)
    {
        return string.replaceAll(
            String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z][0-9])", "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z0-9])(?=[^A-Za-z0-9])"), "-").toLowerCase();
    }

    @Override
    public void generate(OutputStream output) throws IOException
    {
        checkAllRequiredFieldsSet();
        writer = new GeneratorWriter(output);

        writeXmlPreamble();
        writeStartElement();
        writeImports();
        writeAnnotation();
        writeConfigElement();
        writeOperations();
        writeClosingStartElement();

        writer.flush();
    }

    @Override
    protected void checkAllRequiredFieldsSet()
    {
        super.checkAllRequiredFieldsSet();

        if (StringUtils.isEmpty(namespaceIdentifierSuffix))
        {
            throw new IllegalStateException("namespaceIdentifierSuffix is not set");
        }
        if (StringUtils.isEmpty(schemaVersion))
        {
            throw new IllegalStateException("schemaVersion is not set");
        }
    }

    private void writeXmlPreamble() throws IOException
    {
        writer.writeLine("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
    }

    private void writeStartElement() throws IOException
    {
        writer.writeLine("<xsd:schema xmlns=\"http://www.mulesoft.org/schema/mule/%1s\"",
            namespaceIdentifierSuffix);

        writer.indentDepth(12);
        writer.writeLine("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
        writer.writeLine("xmlns:mule=\"http://www.mulesoft.org/schema/mule/core\"");
        writer.writeLine("xmlns:schemadoc=\"http://www.mulesoft.org/schema/mule/schemadoc\"");
        writer.writeLine("xmlns:beans=\"http://www.springframework.org/schema/beans\"");
        writer.writeLine("targetNamespace=\"http://www.mulesoft.org/schema/mule/%1s\"",
            namespaceIdentifierSuffix);
        writer.writeLine("elementFormDefault=\"qualified\"");
        writer.writeLine("attributeFormDefault=\"unqualified\">");
        writer.resetIndentDepth();
    }

    private void writeImports() throws IOException
    {
        writer.newLine();
        writer.indentDepth(4);
        writer.writeLine("<xsd:import namespace=\"http://www.w3.org/XML/1998/namespace\"/>");

        writer.writeLine("<xsd:import namespace=\"http://www.springframework.org/schema/beans\"");
        writer.writeLine("            schemaLocation=\"http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\"/>");

        writer.writeLine("<xsd:import namespace=\"http://www.mulesoft.org/schema/mule/core\"");
        writer.writeLine("            schemaLocation=\"http://www.mulesoft.org/schema/mule/core/%1s/mule.xsd\"/>",
            schemaVersion);

        writer.writeLine("<xsd:import namespace=\"http://www.mulesoft.org/schema/mule/schemadoc\"");
        writer.writeLine("            schemaLocation=\"http://www.mulesoft.org/schema/mule/schemadoc/%1s/mule-schemadoc.xsd\"/>",
            schemaVersion);
        writer.resetIndentDepth();
    }

    private void writeAnnotation() throws IOException
    {
        writer.newLine();
        writer.indentDepth(4);
        writer.writeLine("<xsd:annotation>");
        writer.writeLine("    <xsd:documentation>");
        writer.writeLine("        This schema was auto-generated. Do not edit.");
        writer.writeLine("    </xsd:documentation>");
        writer.writeLine("</xsd:annotation>");
        writer.resetIndentDepth();
    }

    private void writeConfigElement() throws IOException
    {
        List<JavaMethod> setterMethods = JavaClassUtils.collectSetters(javaClass);
        if (setterMethods.size() > 0)
        {
            writer.newLine();
            writer.writeLine("    <!-- Configration -->");
            writeConfigXmlElement();
            writeConfigElementType(setterMethods);
        }
    }

    private void writeConfigXmlElement() throws IOException
    {
        writer.write("    ");
        writer.writeLine("<xsd:element name=\"config\" type=\"configType\" substitutionGroup=\"mule:abstract-extension\"/>");
    }

    private void writeConfigElementType(List<JavaMethod> gettersAndSetters) throws IOException
    {
        writer.indentDepth(4);
        writer.writeLine("<xsd:complexType name=\"configType\">");
        writer.writeLine("    <xsd:complexContent>");
        writer.writeLine("        <xsd:extension base=\"mule:abstractExtensionType\">");

        for (JavaMethod method : gettersAndSetters)
        {
            // strip 'set' from the method name
            String methodName = method.getName().substring(3);
            methodName = StringUtils.uncapitalize(methodName);

            writer.write("                <xsd:attribute name=\"");
            writer.write(methodName);
            writer.write("\" type=\"");

            String type = method.getParameters()[0].getType().getValue();
            writer.write(SchemaTypesMapping.schemaTypeForJavaTypeName(type));
            writer.write("\">");
            writer.newLine();

            if (StringUtils.isNotBlank(method.getComment()))
            {
                writer.indentDepth(20);
                new JavadocToSchemadocTransformer(method.getComment()).generate(writer);
                writer.indentDepth(4);
            }

            writer.writeLine("            </xsd:attribute>");
        }
        writer.writeLine("        </xsd:extension>");
        writer.writeLine("    </xsd:complexContent>");
        writer.writeLine("</xsd:complexType>");
        writer.resetIndentDepth();
    }

    private void writeOperations() throws IOException
    {
        writer.newLine();
        writer.writeLine("    <!-- Operations -->");

        for (JavaMethod method : javaClass.getMethods())
        {
            writeOperation(method);
        }
    }

    private void writeOperation(JavaMethod method) throws IOException
    {
        if (isValidMethod(method))
        {
            writeOperationElementDeclaration(method);
            writeOperationElementType(method);
            writer.newLine();
        }
    }

    /**
     * Ignore getters and setters, they are only needed for Spring to put configuration values
     * into the instance. Ignore non-public methods.
     */
    private boolean isValidMethod(JavaMethod method)
    {
        boolean isNotPublic = !method.isPublic();
        boolean isGetMethod = method.getName().startsWith("get");
        boolean isSetMethod = JavaClassUtils.isSetterMethod(method);
        boolean hasParameters = method.getParameters().length > 0;

        if (isNotPublic || isSetMethod || (isGetMethod && !hasParameters))
        {
            return false;
        }
        return true;
    }

    private void writeOperationElementDeclaration(JavaMethod method) throws IOException
    {
        String elementName = splitCamelCase(method.getName());

        writer.write("    <xsd:element name=\"");
        writer.write(elementName);
        writer.write("\" type=\"");
        writer.write(method.getName());
        writer.write("Type\" substitutionGroup=\"mule:abstract-message-processor\">");
        writer.newLine();

        writer.indentDepth(8);
        new JavadocToSchemadocTransformer(method.getComment()).generate(writer);
        writer.resetIndentDepth();

        writer.writeLine("    </xsd:element>");
    }

    private void writeOperationElementType(JavaMethod method) throws IOException
    {
        writer.indentDepth(4);
        writer.writeLine("<xsd:complexType name=\"%1sType\">", method.getName());
        writer.writeLine("    <xsd:complexContent>");
        writer.writeLine("        <xsd:extension base=\"mule:abstractInterceptingMessageProcessorType\">");

        for (JavaParameter parameter : method.getParameters())
        {
            String type = schemaTypeForParameter(parameter, method);
            writer.writeLine("            <xsd:attribute name=\"%1s\" type=\"%2s\"/>",
                parameter.getName(), type);
        }

        writer.writeLine("        </xsd:extension>");
        writer.writeLine("    </xsd:complexContent>");
        writer.writeLine("</xsd:complexType>");
        writer.resetIndentDepth();
    }

    private String schemaTypeForParameter(JavaParameter parameter, JavaMethod method)
    {
        try
        {
            return SchemaTypesMapping.schemaTypeForJavaTypeName(parameter.getType().getValue());
        }
        catch (IllegalStateException ise)
        {
            String message = String.format("Don't know how to map the type of parameter %1s (method: %2s). Parameter type is %3s",
                parameter.getName(), method.getName(), parameter.getType().getValue());
            throw new IllegalStateException(message);
        }
    }

    private void writeClosingStartElement() throws IOException
    {
        writer.writeLine("</xsd:schema>");
    }

    public void setNamespaceIdentifierSuffix(String suffix)
    {
        namespaceIdentifierSuffix = suffix;
    }

    public void setSchemaVersion(String version)
    {
        schemaVersion = version;
    }
}
