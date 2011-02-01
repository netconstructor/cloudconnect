/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cloudconnect.parser.qdox;

import org.mule.tools.cloudconnect.model.JavaModel;
import org.mule.tools.cloudconnect.parser.ClassParseException;
import org.mule.tools.cloudconnect.parser.ClassParser;
import org.mule.tools.cloudconnect.parser.ClassParserLog;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.parser.ParseException;

import java.io.File;

public class QDoxClassParser implements ClassParser
{

    private ClassParserLog log;
    private JavaDocBuilder javaDocBuilder;

    public QDoxClassParser()
    {
        this.javaDocBuilder = new JavaDocBuilder();
    }

    public void setLog(ClassParserLog log)
    {
        this.log = log;
    }

    protected void setJavaDocBuilder(JavaDocBuilder javaDocBuilder)
    {
        this.javaDocBuilder = javaDocBuilder;
    }

    public void addSourceTree(File sourceTree)
    {
        javaDocBuilder.addSourceTree(sourceTree);
    }

    public JavaModel parse() throws ClassParseException
    {
        JavaModel model = new JavaModel();

        try
        {
            if (javaDocBuilder.getClasses() == null)
            {
                throw new ClassParseException("Sources does not contain any class");
            }

            for (int i = 0; i < javaDocBuilder.getClasses().length; i++)
            {
                model.addClass(new QDoxClassAdapter(javaDocBuilder.getClasses()[i], model));
            }
        }
        catch (ParseException pe)
        {
            throw new ClassParseException("Cannot parse", pe);
        }

        return model;
    }
}
