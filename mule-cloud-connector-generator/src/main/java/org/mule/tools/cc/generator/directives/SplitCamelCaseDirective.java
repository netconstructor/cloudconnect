package org.mule.tools.cc.generator.directives;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

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

        SplitCamelCaseWriter(Writer out)
        {
            this.out = out;
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
