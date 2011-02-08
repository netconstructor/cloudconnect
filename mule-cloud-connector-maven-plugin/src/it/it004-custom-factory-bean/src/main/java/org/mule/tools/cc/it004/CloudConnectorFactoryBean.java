/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cc.it004;

import org.springframework.beans.factory.FactoryBean;

public class CloudConnectorFactoryBean implements FactoryBean<CloudConnector>
{
    public CloudConnector getObject() throws Exception
    {
        return new CloudConnector();
    }

    public Class<CloudConnector> getObjectType()
    {
        return CloudConnector.class;
    }

    public boolean isSingleton()
    {
        return true;
    }
}