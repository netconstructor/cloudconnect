package org.mule.tools.cc.generator.directives;

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class UncapitalizeDirective implements TemplateDirectiveModel
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
            templateDirectiveBody.render(new UncapitalizeWriter(environment.getOut()));
        }
        else
        {
            throw new RuntimeException("Missing body");
        }
    }

    private static class UncapitalizeWriter extends Writer
    {
        private final Writer out;

        UncapitalizeWriter(Writer out)
        {
            this.out = out;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException
        {
            String str = new String(cbuf, off, len);

            out.write(StringUtils.uncapitalize(str));
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
