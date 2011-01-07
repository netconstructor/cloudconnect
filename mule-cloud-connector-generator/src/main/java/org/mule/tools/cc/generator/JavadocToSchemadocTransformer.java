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

import org.apache.commons.lang.StringUtils;

public class JavadocToSchemadocTransformer
{
    private String javadocText;
    private GeneratorWriter writer;

    public JavadocToSchemadocTransformer(String javadoc)
    {
        super();
        javadocText = javadoc;
    }

    public void generate(GeneratorWriter generatorWriter) throws IOException
    {
        this.writer = generatorWriter;

        if (StringUtils.isNotEmpty(javadocText))
        {
            writer.indentDepth(8);

            generateOpeningAnnotationAndDocumentationElement();
            translateJavadocText();
            generateClosingAnnotationAndDocumentationElement();

            writer.resetIndentDepth();
        }
    }

    private void generateOpeningAnnotationAndDocumentationElement() throws IOException
    {
        writer.writeLine("<xsd:annotation>");
        writer.writeLine("    <xsd:documentation>");
    }

    private void translateJavadocText() throws IOException
    {
        writer.write("        ");
        writer.writeLine(javadocText);
    }

    private void generateClosingAnnotationAndDocumentationElement() throws IOException
    {
        writer.writeLine("    </xsd:documentation>");
        writer.writeLine("</xsd:annotation>");
    }
}
