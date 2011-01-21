
package org.mule.tools.cc.generator;

import java.util.HashMap;
import java.util.Map;

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
        root.put("class", javaClass);
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
