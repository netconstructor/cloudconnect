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

package org.mule.tools.cloudconnect.generator.directives;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class TypeClassDirective implements TemplateDirectiveModel
{
    public static final String ARRAY_SUFFIX = "[]";

    @SuppressWarnings("rawtypes")
    public void execute(Environment environment,
                        Map params,
                        TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException
    {
        if (!params.isEmpty())
        {
            throw new TemplateModelException("This directive doesn't allow any parameter");
        }
        if (templateModels.length != 0)
        {
            throw new TemplateModelException("This directive doesn't allow loop variables.");
        }

        if (templateDirectiveBody != null)
        {
            templateDirectiveBody.render(new TypeClassWriter(environment.getOut()));
        }
        else
        {
            throw new RuntimeException("Missing body");
        }
    }

    private static class TypeClassWriter extends Writer
    {

        private final Writer out;

        TypeClassWriter(Writer output)
        {
            this.out = output;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException
        {
            String type = new String(cbuf, off, len);
            System.out.println("TYPE: " + type);

            if (type.endsWith(ARRAY_SUFFIX))
            {
                String elementClassName = type.substring(0, type.length() - ARRAY_SUFFIX.length());
                out.write("Array.newInstance(" + getClassName(elementClassName) + ", 0).getClass()");
            }
            else
            {
                out.write(getClassName(type));
            }
        }

        private String getClassName(String type)
        {
            if ("int".equals(type))
            {
                return "Integer.TYPE";
            }
            else if ("long".equals(type))
            {
                return "Long.TYPE";
            }
            else if ("double".equals(type))
            {
                return "Double.TYPE";
            }
            else if ("float".equals(type))
            {
                return "Float.TYPE";
            }
            else if ("bool".equals(type))
            {
                return "Boolean.TYPE";
            }
            else if ("char".equals(type))
            {
                return "Character.TYPE";
            }
            else if ("byte".equals(type))
            {
                return "Byte.TYPE";
            }
            else if ("void".equals(type))
            {
                return "Void.TYPE";
            }
            else if ("short".equals(type))
            {
                return "Short.TYPE";
            }
            else
            {
                return "Class.forName(\"" + type + "\")";
            }
        }

        @Override
        public void flush
                () throws IOException
        {
            out.flush();
        }

        @Override
        public void close
                () throws IOException
        {
            out.close();
        }
    }
}