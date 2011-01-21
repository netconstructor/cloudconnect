/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cc.generator;

import com.thoughtworks.qdox.model.JavaClass;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.mule.tools.cc.generator.directives.SplitCamelCaseDirective;
import org.mule.tools.cc.generator.directives.TypeMapDirective;
import org.mule.tools.cc.generator.directives.UncapitalizeDirective;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

public abstract class AbstractTemplateGenerator
{
    private static final String TEMPLATES_DIRECTORY = "/org/mule/tools/cc/generator/templates";

    protected JavaClass javaClass;

    public void generate(OutputStream output) throws IOException
    {
        Configuration cfg = createConfiguration();
        Template temp = cfg.getTemplate(getTemplate());
        Map<String, Object> model = createModel();

        write(output, temp, model);
    }

    protected abstract Map<String, Object> createModel();

    protected abstract String getTemplate();

    protected void checkAllRequiredFieldsSet()
    {
        if (javaClass == null)
        {
            throw new IllegalStateException("javaClass is not set");
        }
    }

    public void setJavaClass(JavaClass klass)
    {
        javaClass = klass;
    }

    private Configuration createConfiguration() throws IOException
    {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(getClass(), TEMPLATES_DIRECTORY);
        cfg.setSharedVariable("splitCamelCase", new SplitCamelCaseDirective());
        cfg.setSharedVariable("uncapitalize", new UncapitalizeDirective());
        cfg.setSharedVariable("typeMap", new TypeMapDirective());

        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        BeansWrapper bw = (BeansWrapper) cfg.getObjectWrapper();
        bw.setSimpleMapWrapper(true);
        bw.setExposureLevel(BeansWrapper.EXPOSE_ALL);
        cfg.setObjectWrapper(bw);
        return cfg;
    }

    private void write(OutputStream output, Template temp, Map<String, Object> model) throws IOException
    {
        Writer out = new OutputStreamWriter(output);
        try
        {
            temp.process(model, out);
        }
        catch (TemplateException e)
        {
            throw new RuntimeException("Unable to generate namespace handler template", e);
        }
        out.flush();
    }
}


