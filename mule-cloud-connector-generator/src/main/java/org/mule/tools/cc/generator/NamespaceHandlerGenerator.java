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

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class NamespaceHandlerGenerator extends AbstractGenerator
{
    private static final String TEMPLATES_DIRECTORY = "/org/mule/tools/cc/generator/templates";
    private static final String NAMESPACE_HANDLER_TEMPLATE = "namespacehandler.ftl";

    private String packageName;
    private String className;

    @Override
    public void generate(OutputStream output) throws IOException
    {
        Configuration cfg = createConfiguration();
        Template temp = cfg.getTemplate(NAMESPACE_HANDLER_TEMPLATE);
        Map<String, Object> model = createModel();

        write(output, temp, model);
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

    private Map<String, Object> createModel()
    {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("packageName", packageName);
        root.put("className", className);
        root.put("javaClass", javaClass);
        root.put("hasSetters", JavaClassUtils.collectSetters(javaClass).size() > 0);
        root.put("operations", JavaClassUtils.collectOperations(javaClass));
        return root;
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

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }
}
