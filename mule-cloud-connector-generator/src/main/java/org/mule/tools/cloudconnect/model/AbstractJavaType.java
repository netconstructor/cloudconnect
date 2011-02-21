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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractJavaType implements JavaType
{
    private static final Map<String, String> TYPES_MAP;

    static
    {
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put("boolean", "xsd:boolean");
        mapping.put(Boolean.class.getName(), "xsd:boolean");
        mapping.put("int", "xsd:integer");
        mapping.put("long", "xsd:long");
        mapping.put(Integer.class.getName(), "xsd:integer");
        mapping.put(String.class.getName(), "xsd:string");
        mapping.put("java.lang.Date", "xsd:date");
        mapping.put("java.lang.Class", "xsd:string");
        mapping.put("java.net.URL", "xsd:string");
        mapping.put("java.util.Map", "xsd:string");
        mapping.put("org.w3c.dom.Node", "xsd:string");

        TYPES_MAP = Collections.unmodifiableMap(mapping);
    }

    public String getXmlType()
    {
        if( isEnum() )
            return getName() + "Enum";

        return TYPES_MAP.get(getName());
    }
}
