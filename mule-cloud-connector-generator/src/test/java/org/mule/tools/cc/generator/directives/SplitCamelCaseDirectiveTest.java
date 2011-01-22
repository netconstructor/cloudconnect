package org.mule.tools.cc.generator.directives;

import freemarker.template.TemplateDirectiveModel;
import org.junit.Test;

public class SplitCamelCaseDirectiveTest extends AbstractDirectiveTest
{

    @Override
    protected TemplateDirectiveModel createDirective()
    {
        return new SplitCamelCaseDirective();
    }

    @Test
    public void splitCamelCase() throws Exception
    {
        testDirective("thisIsAMethodName", "this-is-amethod-name");
    }
}
