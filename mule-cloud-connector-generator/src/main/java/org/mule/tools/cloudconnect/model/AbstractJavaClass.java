/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
