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

public abstract class AbstractJavaClass implements JavaClass
{

    public boolean isConnector()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Connector"))
            {
                return true;
            }
        }

        return false;
    }

    public String getNamespacePrefix()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Connector") &&
                annotation.getNamedParameter("namespacePrefix") != null)
            {
                return ((String)annotation.getNamedParameter("namespacePrefix")).replace("\"", "");
            }
        }

        return null;
    }

    public String getNamespaceUri()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Connector") &&
                annotation.getNamedParameter("namespaceUri") != null)
            {
                return ((String)annotation.getNamedParameter("namespaceUri")).replace("\"", "");
            }
        }

        if (getNamespacePrefix().length() > 0)
        {
            return "http://www.mulesoft.org/schema/mule/" + getNamespacePrefix();
        }

        return null;
    }

    public String getMuleVersion()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Connector") &&
                annotation.getNamedParameter("muleVersion") != null)
            {
                return ((String)annotation.getNamedParameter("muleVersion")).replace("\"", "");
            }
        }

        return "3.1";
    }

    public JavaClass getFactory()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Connector") &&
                annotation.getNamedParameter("factory") != null)
            {
                String fqClassName = ((String)annotation.getNamedParameter("factory")).replace("\"", "").replace(".class", "");
                for( JavaClass clazz : getParentModel().getClasses() )
                {
                    if( clazz.getFullyQualifiedName().equals(fqClassName ))
                    {
                        return clazz;
                    }
                }
            }
        }

        return null;
    }
}
