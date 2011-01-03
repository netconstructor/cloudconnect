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

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class TestUtils
{
    public static InputStream getTestResource(String filename)
    {
        InputStream input = TestUtils.class.getClassLoader().getResourceAsStream(filename);
        assertNotNull(input);
        return input;
    }

    private TestUtils()
    {
        // do not instantiate
    }
}
