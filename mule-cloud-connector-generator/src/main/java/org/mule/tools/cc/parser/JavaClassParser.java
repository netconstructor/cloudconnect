/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cc.parser;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.parser.ParseException;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JavaClassParser
{
    public JavaClass parse(InputStream input)
    {
        try
        {
            JavaDocBuilder builder = new JavaDocBuilder();
            builder.addSource(new InputStreamReader(input));

            JavaClass[] typeDeclarations = builder.getClasses();
            if (typeDeclarations == null)
            {
                throw new IllegalArgumentException("Source file does not contain a Java class");
            }

            if (typeDeclarations.length > 1)
            {
                throw new IllegalArgumentException("Source file contains more than one Java class");
            }

            return typeDeclarations[0];
        }
        catch (ParseException pe)
        {
            throw new IllegalArgumentException(pe);
        }
    }
}
