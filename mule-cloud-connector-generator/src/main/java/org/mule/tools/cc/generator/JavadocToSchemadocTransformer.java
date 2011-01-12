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
import org.apache.commons.lang.text.StrTokenizer;

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
            generateOpeningAnnotationAndDocumentationElement();
            translateJavadocText();
            generateClosingAnnotationAndDocumentationElement();

            writer.flush();
        }
    }

    private void generateOpeningAnnotationAndDocumentationElement() throws IOException
    {
        writer.writeLine("<xsd:annotation>");
        writer.writeLine("    <xsd:documentation>");
    }

    private void translateJavadocText() throws IOException
    {
        writer.indent();
        writer.write("       ");
        
        StrTokenizer tokenizer = new StrTokenizer(javadocText);
        String token = tokenizer.nextToken();
        while (token != null)
        {
            String outputToken = trimAndStripJavadocPrefix(token);
            if (StringUtils.isNotBlank(outputToken))
            {
                writer.write(" ");
                writer.write(outputToken);
            }

            token = tokenizer.nextToken();
        }

        writer.newLine();
    }

    private String trimAndStripJavadocPrefix(String token)
    {
        String processedToken = token.trim();
        if (processedToken.startsWith("*"))
        {
            processedToken = processedToken.substring(1);
            processedToken = processedToken.trim();
        }
        return processedToken;
    }

    private void generateClosingAnnotationAndDocumentationElement() throws IOException
    {
        writer.writeLine("    </xsd:documentation>");
        writer.writeLine("</xsd:annotation>");
    }
}
