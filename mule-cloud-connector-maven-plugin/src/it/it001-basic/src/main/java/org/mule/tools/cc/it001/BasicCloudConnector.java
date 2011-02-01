package org.mule.tools.cc.it001;

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

@Connector(namespacePrefix="basic")
public class BasicCloudConnector
{
	@Operation
    public int sum(int a, int b)
    {
        return a + b;
    }
}