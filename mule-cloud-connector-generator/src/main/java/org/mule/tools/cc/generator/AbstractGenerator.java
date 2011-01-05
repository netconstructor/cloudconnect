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

public abstract class AbstractGenerator
{
    protected GeneratorWriter writer;
    protected JavaClass javaClass;

    public abstract void generate(OutputStream output) throws IOException;

    protected void checkAllRequiredFieldsSet()
    {
        if (javaClass == null)
        {
            throw new IllegalStateException("javaClass is not set");
        }
    }

    public void setJavaClass(JavaClass klass)
    {
        javaClass = klass;
    }
}


