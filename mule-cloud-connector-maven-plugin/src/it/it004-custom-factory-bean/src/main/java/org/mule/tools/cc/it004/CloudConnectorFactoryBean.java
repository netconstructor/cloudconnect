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