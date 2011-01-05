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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class UnitTestUtils
{
    public static InputStream getTestResource(String filename)
    {
        InputStream input = UnitTestUtils.class.getClassLoader().getResourceAsStream(filename);
        assertNotNull(input);
        return input;
    }

    public static void runGeneratorAndCompareTo(AbstractGenerator generator, String filename,
        boolean printGeneratedOutput) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream(1500);
        generator.generate(output);

        if (printGeneratedOutput)
        {
            System.out.println(output);
        }

        InputStream sourceInput = new ByteArrayInputStream(output.toByteArray());
        InputStream controlInput = UnitTestUtils.getTestResource(filename);
        new LineByLineComparator(sourceInput, controlInput).compare();
    }

    private UnitTestUtils()
    {
        // do not instantiate
    }
}
