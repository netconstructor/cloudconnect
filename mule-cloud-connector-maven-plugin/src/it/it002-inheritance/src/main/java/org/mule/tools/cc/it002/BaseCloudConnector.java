package org.mule.tools.cc.it002;

import org.mule.tools.cloudconnect.annotations.Operation;

public abstract class BaseCloudConnector
{
    @Operation
    public void sendMessage(String message)
    {
    }
}