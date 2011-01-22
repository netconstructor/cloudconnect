package org.mule.tools.cc.generator.directives;

import freemarker.template.TemplateDirectiveModel;
import org.junit.Test;

public class UncapitalizeDirectiveTest extends AbstractDirectiveTest
{

    @Override
    protected TemplateDirectiveModel createDirective()
    {
        return new UncapitalizeDirective();
    }

    @Test
    public void uncapitalize() throws Exception
    {
        testDirective("Uncap", "uncap");
    }
}
