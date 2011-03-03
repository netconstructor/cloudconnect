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

import org.mule.tools.cloudconnect.model.JavaClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringSchemaGenerator extends AbstractTemplateGenerator
{

    private static final String SCHEMA_TEMPLATE = "springschema.ftl";
    private String schemaVersion;
    private List<JavaClass> classes;

    @Override
    protected Map<String, Object> createModel()
    {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("classes", classes);
        root.put("schemaVersion", schemaVersion);
        return root;
    }

    @Override
    protected String getTemplate()
    {
        return SCHEMA_TEMPLATE;
    }

    public String getSchemaVersion()
    {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion)
    {
        this.schemaVersion = schemaVersion;
    }

    public List<JavaClass> getClasses()
    {
        return classes;
    }

    public void setClasses(List<JavaClass> classes)
    {
        this.classes = classes;
    }
}
