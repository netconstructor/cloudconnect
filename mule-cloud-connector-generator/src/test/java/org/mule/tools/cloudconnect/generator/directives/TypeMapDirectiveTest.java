package org.mule.tools.cloudconnect.generator.directives;

import freemarker.template.TemplateDirectiveModel;
import org.junit.Test;

public class TypeMapDirectiveTest extends AbstractDirectiveTest
{

    @Override
    protected TemplateDirectiveModel createDirective()
    {
        return new TypeMapDirective();
    }

    @Test
    public void integer() throws Exception
    {
        testDirective("int", "xsd:integer");
    }

    @Test
    public void date() throws Exception
    {
        testDirective("java.lang.Date", "xsd:date");
    }

    @Test
    public void str() throws Exception
    {
        testDirective("java.lang.String", "xsd:string");
    }

    @Test
    public void bool() throws Exception
    {
        testDirective("boolean", "xsd:boolean");
    }

    @Test
    public void boolClass() throws Exception
    {
        testDirective("java.lang.Boolean", "xsd:boolean");
    }

    @Test
    public void integerClass() throws Exception
    {
        testDirective("java.lang.Integer", "xsd:integer");
    }

}
