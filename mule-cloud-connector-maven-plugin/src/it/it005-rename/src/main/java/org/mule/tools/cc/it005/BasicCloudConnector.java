package org.mule.tools.cc.it005;

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

@Connector(namespacePrefix="basic")
public class BasicCloudConnector
{
	@Operation(name="theSumOfItsParts")
    public int sum(int a, int b)
    {
        return a + b;
    }
}