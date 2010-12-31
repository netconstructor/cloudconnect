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

public class GeneratorWriter
{
    private BufferedWriter writer;
    private int indentDepth;

    public GeneratorWriter(OutputStream output)
    {
        super();
        OutputStreamWriter osw = new OutputStreamWriter(output);
        writer = new BufferedWriter(osw);
        resetIndentDepth();
    }

    public void writeLine(String format, Object... arguments) throws IOException
    {
        String text = String.format(format, arguments);
        writeLine(text);
    }

    public void writeLine(String string) throws IOException
    {
        for (int i = 0; i < indentDepth; i++)
        {
            writer.write(' ');
        }
        writer.write(string);
        writer.newLine();
    }

    public void write(String string) throws IOException
    {
        writer.write(string);
    }

    public void newLine() throws IOException
    {
        writer.newLine();
    }

    public void flush() throws IOException
    {
        writer.flush();
    }

    public void indentDepth(int depth)
    {
        this.indentDepth = depth;
    }

    public void resetIndentDepth()
    {
        indentDepth = 0;
    }
}
