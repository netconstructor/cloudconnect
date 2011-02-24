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

package org.mule.tools.cloudconnect.generator;

import org.mule.tools.cloudconnect.model.JavaType;

import java.util.HashMap;
import java.util.Map;

public class EnumTransformerGenerator extends AbstractTemplateGenerator
{

    private static final String ENUM_TRANSFORMER_TEMPLATE = "enumtransformer.ftl";

    private String packageName;
    private JavaType javaType;

    @Override
    protected Map<String, Object> createModel()
    {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("type", javaType);
        root.put("packageName", packageName);
        return root;
    }

    @Override
    protected String getTemplate()
    {
        return ENUM_TRANSFORMER_TEMPLATE;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public JavaType getJavaType()
    {
        return javaType;
    }

    public void setJavaType(JavaType javaType)
    {
        this.javaType = javaType;
    }
}