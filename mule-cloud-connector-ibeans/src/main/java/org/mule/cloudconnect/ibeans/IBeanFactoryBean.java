/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
