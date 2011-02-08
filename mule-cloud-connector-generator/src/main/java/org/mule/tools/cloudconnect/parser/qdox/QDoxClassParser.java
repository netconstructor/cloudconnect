/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
