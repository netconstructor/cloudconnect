package org.mule.tools.cc;

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
    private File cloudConnector;

    public String getSchemaFilename()
    {
        return schemaFilename;
    }

    public File getNamespaceHandler()
    {
        return namespaceHandler;
    }

    public File getCloudConnector()
    {
        return cloudConnector;
    }
}
