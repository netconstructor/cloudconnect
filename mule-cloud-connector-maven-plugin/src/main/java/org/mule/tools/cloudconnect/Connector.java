package org.mule.tools.cloudconnect;

import java.io.File;

public class Connector
{
    /**
     * Filename of the generated schema file.
     *
     * @parameter
     * @required
     */
    private String schemaFilename;
    /**
     * Absolute path to the generated namespace handler source file.
     *
     * @parameter
     * @required
     */
    private File namespaceHandler;

    /**
     * Absolute path to the Java source file of the cloud connector class.
     *
     * @parameter
     * @required
     */
    private String cloudConnectorClass;

    /**
     * Name of the class that will be used as a factory bean.
     * @parameter
     */
    private String factoryBean;

    public String getSchemaFilename()
    {
        return schemaFilename;
    }

    public File getNamespaceHandler()
    {
        return namespaceHandler;
    }

    public String getCloudConnectorClass()
    {
        return cloudConnectorClass;
    }

    public String getFactoryBean()
    {
        return factoryBean;
    }
}
