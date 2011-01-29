/*
 * $Id: TwitterIBeanFactoryBean.java 269 2010-12-27 12:51:03Z dfeist $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.cloudconnect.ibeans;

import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.module.ibeans.config.IBeanBinding;
import org.mule.module.ibeans.config.IBeanFlowConstruct;
import org.mule.module.ibeans.spi.MuleIBeansPlugin;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.FactoryBean;

public abstract class IBeanFactoryBean<T> implements FactoryBean<T>, MuleContextAware
{

    protected MuleContext muleContext;

    public T getObject() throws Exception
    {
        IBeanBinding binding = createBinding(getObjectType().getSimpleName());
        binding.setInterface(getObjectType());
        T ibean = (T) binding.createProxy(new Object());
        init(ibean);

        return ibean;
    }

    public abstract void init(T object);

    @SuppressWarnings("unchecked")
    public Class<T> getObjectType()
    {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    public boolean isSingleton()
    {
        return true;
    }

    public void setMuleContext(MuleContext context)
    {
        this.muleContext = context;

    }

    protected IBeanBinding createBinding(String name)
    {
        return new IBeanBinding(new IBeanFlowConstruct(name + ".ibean", muleContext), muleContext,
                                new MuleIBeansPlugin(muleContext));
    }
}
