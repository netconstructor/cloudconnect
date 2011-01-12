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

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavadocToSchemadocTransformerTestCase
{
    private ByteArrayOutputStream bytesOut;
    private GeneratorWriter writer;

    @Before
    public void createWriters()
    {
        bytesOut = new ByteArrayOutputStream();
        writer = new GeneratorWriter(bytesOut);
    }

    @Test
    public void nullInput() throws Exception
    {
        new JavadocToSchemadocTransformer(null).generate(writer);
        assertEquals("", bytesOut.toString());
    }

    @Test
    public void emptyStringInput() throws Exception
    {
        new JavadocToSchemadocTransformer("").generate(writer);
        assertEquals("", bytesOut.toString());
    }

    @Test
    public void emptyJavadocCommentInput() throws Exception
    {
        String input = "                \n"
            + "     * Looks up the city\n"
            + "     \n";

        new JavadocToSchemadocTransformer(input).generate(writer);
        String expected = annotationContaining("Looks up the city");
        String actual = bytesOut.toString();
        assertEquals(expected, actual);
    }

    private String annotationContaining(String string)
    {
        StringBuilder buf = new StringBuilder(512);
        buf.append("<xsd:annotation>\n");
        buf.append("    <xsd:documentation>\n");
        buf.append("        ");
        buf.append(string);
        buf.append("\n");
        buf.append("    </xsd:documentation>\n");
        buf.append("</xsd:annotation>\n");

        return buf.toString();
    }
}
