package org.mule.tools.cc.it003;

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

@Connector(namespacePrefix="enum")
public class InnerEnumCloudConnector
{
    public enum Format
    {
        XML,
        JSON
    }

    @Operation
    public void format(Format format)
    {
    }
}