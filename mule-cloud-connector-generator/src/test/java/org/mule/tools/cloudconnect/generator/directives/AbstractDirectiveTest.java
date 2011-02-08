/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cloudconnect.generator.directives;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import org.junit.Test;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public abstract class AbstractDirectiveTest
{

    @Test(expected = TemplateException.class)
    public void withParameters() throws Exception
    {
        Environment environment = Environment.getCurrentEnvironment();
        Map map = mock(Map.class);
        when(map.isEmpty()).thenReturn(false);
        TemplateModel[] templateModel = new TemplateModel[] {};
        TemplateDirectiveBody templateDirectiveBody = mock(TemplateDirectiveBody.class);

        TemplateDirectiveModel directive = createDirective();
        directive.execute(environment, map, templateModel, templateDirectiveBody);
    }

    @Test(expected = TemplateException.class)
    public void withModels() throws Exception
    {
        Environment environment = Environment.getCurrentEnvironment();
        Map map = mock(Map.class);
        when(map.isEmpty()).thenReturn(true);
        TemplateModel templateModel = mock(TemplateModel.class);
        TemplateModel[] templateModels = new TemplateModel[] {templateModel};
        TemplateDirectiveBody templateDirectiveBody = mock(TemplateDirectiveBody.class);

        TemplateDirectiveModel directive = createDirective();
        directive.execute(environment, map, templateModels, templateDirectiveBody);
    }

    @Test(expected = RuntimeException.class)
    public void noBody() throws Exception
    {
        Environment environment = Environment.getCurrentEnvironment();
        Map map = mock(Map.class);
        when(map.isEmpty()).thenReturn(true);
        TemplateModel[] templateModels = new TemplateModel[] {};

        TemplateDirectiveModel directive = createDirective();
        directive.execute(environment, map, templateModels, null);
    }

    protected void testDirective(String input, String output) throws Exception
    {
        Writer writer = mock(Writer.class);
        Template template = mock(Template.class);
        TemplateHashModel templateHashModel = mock(TemplateHashModel.class);
        Environment environment = new Environment(template, templateHashModel, writer);

        Map map = mock(Map.class);
        when(map.isEmpty()).thenReturn(true);
        TemplateModel[] templateModels = new TemplateModel[] {};
        TemplateDirectiveBody templateDirectiveBody = new CustomTemplateDirectiveBody(input);

        TemplateDirectiveModel directive = createDirective();
        directive.execute(environment, map, templateModels, templateDirectiveBody);

        verify(writer).write(eq(output));
    }

    protected abstract TemplateDirectiveModel createDirective();

    class CustomTemplateDirectiveBody implements TemplateDirectiveBody
    {

        private String buffer;

        public CustomTemplateDirectiveBody(String buffer)
        {
            this.buffer = buffer;
        }

        public void render(Writer out) throws TemplateException, IOException
        {
            char[] stringArray = buffer.toCharArray();
            out.write(stringArray, 0, stringArray.length);
        }
    }

}