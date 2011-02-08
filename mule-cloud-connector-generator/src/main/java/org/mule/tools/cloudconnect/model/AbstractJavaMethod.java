/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cloudconnect.model;

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
}
