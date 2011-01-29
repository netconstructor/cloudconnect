/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cloudconnect.generator;

import java.util.HashMap;
import java.util.Map;

public class SpringNamespaceHandlerGenerator extends AbstractTemplateGenerator
{

    private static final String NAMESPACE_HANDLER_TEMPLATE = "springnamespacehandler.ftl";

    private String namespaceIdentifierSuffix;
    private String packageName;
    private String className;

    @Override
    protected Map<String, Object> createModel()
    {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("namespaceIdentifierSuffix", namespaceIdentifierSuffix);
        root.put("packageName", packageName);
        root.put("className", className);
        return root;
    }

    @Override
    protected String getTemplate()
    {
        return NAMESPACE_HANDLER_TEMPLATE;
    }

    public String getNamespaceIdentifierSuffix()
    {
        return namespaceIdentifierSuffix;
    }

    public void setNamespaceIdentifierSuffix(String namespaceIdentifierSuffix)
    {
        this.namespaceIdentifierSuffix = namespaceIdentifierSuffix;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }
}
