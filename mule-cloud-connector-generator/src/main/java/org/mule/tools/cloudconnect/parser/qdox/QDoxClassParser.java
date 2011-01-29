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

import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.parser.ClassParseException;
import org.mule.tools.cloudconnect.parser.ClassParser;
import org.mule.tools.cloudconnect.parser.ClassParserLog;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.parser.ParseException;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

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

    public JavaClass parse(String fullClassName) throws ClassParseException
    {
        try
        {
            if (javaDocBuilder.getClasses() == null)
            {
                throw new ClassParseException("Source file does not contain a Java class");
            }

            String classPath = fullClassName.replace(".", File.separator);
            String className = FilenameUtils.getBaseName(classPath);
            String packageName = FilenameUtils.getPathNoEndSeparator(classPath).replace(File.separator, ".");
            for (int i = 0; i < javaDocBuilder.getClasses().length; i++)
            {
                if (javaDocBuilder.getClasses()[i].getName().equals(className)
                    && javaDocBuilder.getClasses()[i].getPackage().getName().equals(packageName))
                {
                    return new QDoxClassAdapter(javaDocBuilder.getClasses()[i]);
                }
            }

            throw new ClassParseException("Cannot find class " + fullClassName);
        }
        catch (ParseException pe)
        {
            throw new ClassParseException("Cannot parse", pe);
        }
    }
}
