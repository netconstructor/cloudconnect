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

package org.mule.tools.cloudconnect.model;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractJavaMethod extends AbstractJavaElement implements JavaMethod
{

    private static final String OBJECT_CLASS_NAME = "Object";

    public boolean isOperation()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Operation"))
            {
                if (isPublic()
                    && !isStatic()
                    && !isPropertyAccessor()
                    && !isPropertyMutator()
                    && !isConstructor()
                    && !OBJECT_CLASS_NAME.equals(getParentClass().getName()))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
    }

    public boolean isTransformer()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.api.annotations.Transformer"))
            {
                if (isPublic()
                    && !isStatic()
                    && !isPropertyAccessor()
                    && !isPropertyMutator()
                    && !isConstructor()
                    && !OBJECT_CLASS_NAME.equals(getParentClass().getName()))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
    }

    @Override
    public String getElementName()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Operation"))
            {
                if (annotation.getNamedParameter("name") != null)
                {
                    return ((String) annotation.getNamedParameter("name")).replace("\"", "");
                }
            }
        }

        return super.getElementName();
    }

    public String getBeanDefinitionParserName()
    {
        return StringUtils.capitalize(getElementName()) + "OperationDefinitionParser";
    }

    public String getMessageProcessorName()
    {
        return StringUtils.capitalize(getElementName()) + "MessageProcessor";
    }

    public boolean hasParameters()
    {
        return getParameters().size() > 0;
    }

}
