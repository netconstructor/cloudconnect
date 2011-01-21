
package org.mule.tools.cc.generator;

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
        mapping.put(Integer.class.getName(), "xsd:integer");
        mapping.put(String.class.getName(), "xsd:string");
        mapping.put("java.lang.Date", "xsd:date");

        TYPES_MAP = Collections.unmodifiableMap(mapping);
    }

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
