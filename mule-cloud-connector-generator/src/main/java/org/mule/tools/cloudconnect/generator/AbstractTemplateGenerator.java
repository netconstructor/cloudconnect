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

package org.mule.tools.cloudconnect.generator;

import org.mule.tools.cloudconnect.generator.directives.CapitalizeDirective;
import org.mule.tools.cloudconnect.generator.directives.IndentDirective;
import org.mule.tools.cloudconnect.generator.directives.MarkdownFillDirective;
import org.mule.tools.cloudconnect.generator.directives.SingularizeDirective;
import org.mule.tools.cloudconnect.generator.directives.SplitCamelCaseDirective;
import org.mule.tools.cloudconnect.generator.directives.TitleCamelCaseDirective;
import org.mule.tools.cloudconnect.generator.directives.TypeClassDirective;
import org.mule.tools.cloudconnect.generator.directives.UncapitalizeDirective;
import org.mule.tools.cloudconnect.model.JavaClass;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * This is the base class for all generators. It receives a JavaClass model
 * and it generates the content based on a FreeMarker template.
 * <p/>
 * To use it you need to extend it and implement createModel and getTemplate methods.
 */
public abstract class AbstractTemplateGenerator
{

    /**
     * Location of the templates
     */
    private static final String TEMPLATES_DIRECTORY = "/org/mule/tools/cloudconnect/generator/templates";

    private JavaClass javaClass;

    /**
     * Generate the content and send it to the output stream
     *
     * @param output The output stream to which the content will be sent to
     * @throws IOException
     */
    public final void generate(OutputStream output) throws IOException
    {
        Configuration cfg = createConfiguration();
        Template temp = cfg.getTemplate(getTemplate());
        Map<String, Object> model = createModel();

        write(output, temp, model);
    }

    /**
     * Create a model and return it as map that will be feeded into the template
     *
     * @return A map containing the model
     */
    protected abstract Map<String, Object> createModel();

    /**
     * Return the location of the template
     *
     * @return A string representing the location of the template
     */
    protected abstract String getTemplate();

    /**
     * Verify method used to make sure that the class is in the correct state before
     * attempting to generate a file
     */
    protected void checkAllRequiredFieldsSet()
    {
        if (javaClass == null)
        {
            throw new IllegalStateException("javaClass is not set");
        }
    }

    /**
     * Set the JavaClass that will be used for the template
     *
     * @param klass The java class
     */
    public void setJavaClass(JavaClass klass)
    {
        javaClass = klass;
    }

    /**
     * Crate a new FreeMarker configuration
     *
     * @return FreeMarker configuration
     */
    private Configuration createConfiguration()
    {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(getClass(), TEMPLATES_DIRECTORY);
        cfg.setSharedVariable("uncapitalize", new UncapitalizeDirective());
        cfg.setSharedVariable("capitalize", new CapitalizeDirective());
        cfg.setSharedVariable("singularize", new SingularizeDirective());
        cfg.setSharedVariable("splitCamelCase", new SplitCamelCaseDirective());
        cfg.setSharedVariable("titleCamelCase", new TitleCamelCaseDirective());
        cfg.setSharedVariable("typeClass", new TypeClassDirective());
        cfg.setSharedVariable("titleFill", new MarkdownFillDirective('='));
        cfg.setSharedVariable("subTitleFill", new MarkdownFillDirective('-'));
        cfg.setSharedVariable("indent", new IndentDirective(4));

        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        BeansWrapper bw = (BeansWrapper) cfg.getObjectWrapper();
        bw.setSimpleMapWrapper(true);
        bw.setExposureLevel(BeansWrapper.EXPOSE_ALL);
        cfg.setObjectWrapper(bw);
        return cfg;
    }

    /**
     * Calls FreeMarker to generate content based on the template feeding it the model and sending
     * the output to the output stream.
     *
     * @param output The stream that will be used to output the content
     * @param temp   The template that will be used to generate the model
     * @param model  The model that will be feeded into the template
     * @throws IOException
     */
    private void write(OutputStream output, Template temp, Map<String, Object> model) throws IOException
    {
        Writer out = new OutputStreamWriter(output);
        try
        {
            temp.process(model, out);
        }
        catch (TemplateException e)
        {
            throw new RuntimeException("Unable to generate code", e);
        }
        out.flush();
    }

    /**
     * Retrieve java class
     * @return Java class model
     */
    public JavaClass getJavaClass()
    {
        return javaClass;
    }
}


