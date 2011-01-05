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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LineByLineComparator
{
    private BufferedReader sourceReader;
    private BufferedReader controlReader;

    public LineByLineComparator(InputStream sourceInput, InputStream controlInput) throws IOException
    {
        sourceReader = new BufferedReader(new InputStreamReader(sourceInput));
        controlReader = new BufferedReader(new InputStreamReader(controlInput));
    }

    public void compare() throws IOException
    {
        int lineNumber = 1;
        String sourceLine = sourceReader.readLine();
        while (sourceLine != null)
        {
            String controlLine = controlReader.readLine();
            if (controlLine.equals(sourceLine) == false)
            {
                throw new AssertionError("Files differ in line " + lineNumber);
            }

            sourceLine = sourceReader.readLine();
            lineNumber++;
        }
    }
}
