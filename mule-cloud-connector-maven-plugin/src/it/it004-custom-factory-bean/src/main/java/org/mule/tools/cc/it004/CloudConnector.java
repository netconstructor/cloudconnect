package org.mule.tools.cc.it004;

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

@Connector(namespacePrefix="factory", factory=CloudConnectorFactoryBean.class)
public class CloudConnector
{
    @Operation
    public int sum(int a, int b)
    {
        return a + b;
    }
}