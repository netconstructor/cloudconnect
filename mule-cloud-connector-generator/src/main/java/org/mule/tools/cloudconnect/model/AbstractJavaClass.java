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

    private static final String XML_TYPE_ANNOTATION = "javax.xml.bind.annotation.XmlType";
    private static final String CONNECTOR_ANNOTATION = "org.mule.tools.cloudconnect.annotations.Connector";
    private static final String FACTORY_ARGUMENT = "factory";
    private static final String NAMESPACE_PREFIX_ARGUMENT = "namespacePrefix";
    private static final String NAMESPACE_URI_ARGUMENT = "namespaceUri";
    private static final String DEFAULT_NAMESPACE = "http://www.mulesoft.org/schema/mule/";
    private static final String MULE_VERSION_ARGUMENT = "muleVersion";

    public boolean isConnector()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals(CONNECTOR_ANNOTATION))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isXmlType()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals(XML_TYPE_ANNOTATION))
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
            if (annotation.getType().equals(CONNECTOR_ANNOTATION) &&
                annotation.getNamedParameter(NAMESPACE_PREFIX_ARGUMENT) != null)
            {
                return ((String) annotation.getNamedParameter(NAMESPACE_PREFIX_ARGUMENT)).replace("\"", "");
            }
        }

        return null;
    }

    public String getNamespaceUri()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals(CONNECTOR_ANNOTATION) &&
                annotation.getNamedParameter(NAMESPACE_URI_ARGUMENT) != null)
            {
                return ((String) annotation.getNamedParameter(NAMESPACE_URI_ARGUMENT)).replace("\"", "");
            }
        }

        if (getNamespacePrefix().length() > 0)
        {
            return DEFAULT_NAMESPACE + getNamespacePrefix();
        }

        return null;
    }

    public String getMuleVersion()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals(CONNECTOR_ANNOTATION) &&
                annotation.getNamedParameter(MULE_VERSION_ARGUMENT) != null)
            {
                return ((String) annotation.getNamedParameter(MULE_VERSION_ARGUMENT)).replace("\"", "");
            }
        }

        return "3.1";
    }

    public JavaClass getFactory()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals(CONNECTOR_ANNOTATION) &&
                annotation.getNamedParameter(FACTORY_ARGUMENT) != null)
            {
                String fqClassName = ((String) annotation.getNamedParameter(FACTORY_ARGUMENT)).replace("\"", "").replace(".class", "");
                for (JavaClass clazz : getParentModel().getClasses())
                {
                    if (clazz.getFullyQualifiedName().equals(fqClassName))
                    {
                        return clazz;
                    }
                }
            }
        }

        return null;
    }

    public String getNamespaceHandlerName()
    {
        return getName() + "NamespaceHandler";
    }

    public String getNamespaceHandlerPackage()
    {
        return getPackage() + ".config";
    }
}
