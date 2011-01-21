
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
import org.mule.tools.cc.generator.directives.SplitCamelCaseDirective;
import org.mule.tools.cc.generator.directives.TypeMapDirective;
import org.mule.tools.cc.generator.directives.UncapitalizeDirective;
import org.mule.tools.cc.parser.JavaClassUtils;

public class SchemaGenerator extends AbstractTemplateGenerator
{
    private static final String SCHEMA_TEMPLATE = "schema.ftl";

    private String namespaceIdentifierSuffix;
    private String schemaVersion;

    @Override
    protected Map<String, Object> createModel()
    {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("namespaceIdentifierSuffix", namespaceIdentifierSuffix);
        root.put("schemaVersion", schemaVersion);
        root.put("hasSetters", JavaClassUtils.collectSetters(javaClass).size() > 0);
        root.put("setters", JavaClassUtils.collectSetters(javaClass));
        root.put("operations", JavaClassUtils.collectOperations(javaClass));
        return root;
    }

    @Override
    protected String getTemplate()
    {
        return SCHEMA_TEMPLATE;
    }

    public String getNamespaceIdentifierSuffix()
    {
        return namespaceIdentifierSuffix;
    }

    public void setNamespaceIdentifierSuffix(String namespaceIdentifierSuffix)
    {
        this.namespaceIdentifierSuffix = namespaceIdentifierSuffix;
    }

    public String getSchemaVersion()
    {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion)
    {
        this.schemaVersion = schemaVersion;
    }
}
