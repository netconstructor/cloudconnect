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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class TypeMapDirective implements TemplateDirectiveModel
{

    private static final Map<String, String> TYPES_MAP;

    static
    {
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put("boolean", "xsd:boolean");
        mapping.put(Boolean.class.getName(), "xsd:boolean");
        mapping.put("int", "xsd:integer");
        mapping.put("long", "xsd:long");
        mapping.put(Integer.class.getName(), "xsd:integer");
        mapping.put(String.class.getName(), "xsd:string");
        mapping.put("java.lang.Date", "xsd:date");
        mapping.put("java.lang.Class", "xsd:string");
        mapping.put("java.net.URL", "xsd:string");
        mapping.put("java.util.Map", "xsd:string");
        mapping.put("org.w3c.dom.Node", "xsd:string");

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
            try
            {
                templateDirectiveBody.render(new TypeMapWriter(environment.getOut()));
            }
            catch (InvalidTypeException ite)
            {
                throw new TemplateModelException(ite);
            }
        }
        else
        {
            throw new RuntimeException("Missing body");
        }
    }

    private static class TypeMapWriter extends Writer
    {

        private final Writer out;

        TypeMapWriter(Writer output)
        {
            this.out = output;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException
        {
            String str = new String(cbuf, off, len);

            String schemaType = TYPES_MAP.get(str);
            if (schemaType == null)
            {
                throw new InvalidTypeException(str);
            }
            out.write(TYPES_MAP.get(str));
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
