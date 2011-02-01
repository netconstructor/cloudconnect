package org.mule.tools.cc.it002;

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

@Connector(namespacePrefix="extended")
public class ExtendedCloudConnector extends BaseCloudConnector
{
    @Operation
    public void sendSecondMessage(String message)
    {

    }
}