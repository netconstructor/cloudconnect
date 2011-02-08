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

public class SplitCamelCaseDirective implements TemplateDirectiveModel
{

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
            templateDirectiveBody.render(new SplitCamelCaseWriter(environment.getOut()));
        }
        else
        {
            throw new RuntimeException("Missing body");
        }
    }

    private static class SplitCamelCaseWriter extends Writer
    {

        private final Writer out;

        SplitCamelCaseWriter(Writer output)
        {
            this.out = output;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException
        {
            String camelCase = new String(cbuf, off, len);

            camelCase = camelCase.replaceAll(
                    String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z][0-9])", "(?<=[^A-Z])(?=[A-Z])",
                                  "(?<=[A-Za-z0-9])(?=[^A-Za-z0-9])"), "-").toLowerCase();

            out.write(camelCase);
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