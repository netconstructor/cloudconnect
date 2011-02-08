/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cc.it002;

import org.mule.tools.cloudconnect.annotations.Operation;

public abstract class BaseCloudConnector
{
    @Operation
    public void sendMessage(String message)
    {
    }
}