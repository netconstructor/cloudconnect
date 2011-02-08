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

package org.mule.tools.cloudconnect.parser.qdox;

import org.mule.tools.cloudconnect.model.AbstractJavaElement;
import org.mule.tools.cloudconnect.model.JavaProperty;

import com.thoughtworks.qdox.model.BeanProperty;

public class QDoxPropertyAdapter extends AbstractJavaElement implements JavaProperty
{

    private BeanProperty javaProperty;

    protected QDoxPropertyAdapter(BeanProperty javaProperty)
    {
        this.javaProperty = javaProperty;
    }

    public String getName()
    {
        return this.javaProperty.getName();
    }

    public String getType()
    {
        return this.javaProperty.getType().getValue();
    }

    public String getDescription()
    {
        if (this.javaProperty.getMutator() != null)
        {
            return this.javaProperty.getMutator().getComment();
        }

        return null;
    }

    public boolean isEnum()
    {
        return javaProperty.getType().getJavaClass().isEnum();
    }

    public String getEnumName()
    {
        return javaProperty.getType().getJavaClass().getName();
    }
}
