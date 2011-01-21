/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cc.generator.directives;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TypeMapDirective implements TemplateDirectiveModel
{
    private static final Map<String, String> TYPES_MAP;

    static
    {
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put("boolean", "xsd:boolean");
        mapping.put(Boolean.class.getName(), "xsd:boolean");
        mapping.put("int", "xsd:integer");
        mapping.put(Integer.class.getName(), "xsd:integer");
        mapping.put(String.class.getName(), "xsd:string");
        mapping.put("java.lang.Date", "xsd:date");

        TYPES_MAP = Collections.unmodifiableMap(mapping);
    }

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
            templateDirectiveBody.render(new TypeMapWriter(environment.getOut()));
        }
        else
        {
            throw new RuntimeException("Missing body");
        }
    }

    private static class TypeMapWriter extends Writer
    {
        private final Writer out;

        TypeMapWriter(Writer out)
        {
            this.out = out;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException
        {
            String str = new String(cbuf, off, len);

            String schemaType = TYPES_MAP.get(str);
            if (schemaType == null)
            {
                String message = String.format("Don't know how to map from Java type '%1s' to schema type",
                    str);
                throw new IllegalStateException(message);
            }
            else
            {
                out.write(schemaType);
            }
        }

        @Override
        public void flush() throws IOException
        {
            out.flush();
        }

        @Override
        public void close() throws IOException
        {
            out.close();
        }
    }
}
