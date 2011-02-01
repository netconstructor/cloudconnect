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

public class NamespaceHandlerGenerator extends AbstractTemplateGenerator
{

    private static final String NAMESPACE_HANDLER_TEMPLATE = "namespacehandler.ftl";

    private String packageName;
    private String className;

    @Override
    protected Map<String, Object> createModel()
    {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("packageName", packageName);
        root.put("className", className);
        root.put("class", getJavaClass());
        return root;
    }

    @Override
    protected String getTemplate()
    {
        return NAMESPACE_HANDLER_TEMPLATE;
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
