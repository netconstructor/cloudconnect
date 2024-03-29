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

public abstract class AbstractJavaParameter extends AbstractJavaAnnotatedElement implements JavaParameter
{

    @Override
    public String getElementName()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Parameter") &&
                annotation.getNamedParameter("name") != null)
            {
                return ((String) annotation.getNamedParameter("name")).replace("\"", "");
            }
        }

        return super.getElementName();
    }

    public boolean isOptional()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Parameter"))
            {
                if (annotation.getNamedParameter("optional") != null)
                {
                    return "true".equals(annotation.getNamedParameter("optional"));
                }
            }
        }

        return false;
    }

    public String getDefaultValue()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Parameter") &&
                annotation.getNamedParameter("defaultValue") != null)
            {
                return ((String) annotation.getNamedParameter("defaultValue")).replace("\"", "");
            }
        }

        return "";
    }

    public boolean hasDefaultValue()
    {
        return getDefaultValue().length() != 0;
    }
}
