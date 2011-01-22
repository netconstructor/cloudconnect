/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cc.parser.qdox;

import org.mule.tools.cc.model.JavaClass;
import org.mule.tools.cc.parser.ClassParseException;
import org.mule.tools.cc.parser.ClassParser;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.parser.ParseException;

import java.io.InputStream;
import java.io.InputStreamReader;

public class QDoxClassParser implements ClassParser
{

    private JavaDocBuilder javaDocBuilder;

    public QDoxClassParser()
    {
        this.javaDocBuilder = new JavaDocBuilder();
    }

    protected void setJavaDocBuilder(JavaDocBuilder javaDocBuilder)
    {
        this.javaDocBuilder = javaDocBuilder;
    }

    public JavaClass parse(InputStream input) throws ClassParseException
    {
        try
        {
            javaDocBuilder.addSource(new InputStreamReader(input));

            if (javaDocBuilder.getClasses() == null)
            {
                throw new ClassParseException("Source file does not contain a Java class");
            }

            if (javaDocBuilder.getClasses().length > 1)
            {
                throw new ClassParseException("Source file contains more than one Java class");
            }

            return new QDoxClassAdapter(javaDocBuilder.getClasses()[0]);
        }
        catch (ParseException pe)
        {
            throw new ClassParseException("Cannot parse", pe);
        }
    }
}
