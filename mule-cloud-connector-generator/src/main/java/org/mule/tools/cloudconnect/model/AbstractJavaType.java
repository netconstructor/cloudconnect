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
        mapping.put("boolean", "mule:substitutableBoolean");
        mapping.put(Boolean.class.getName(), "mule:substitutableBoolean");
        mapping.put("int", "mule:substitutableInt");
        mapping.put(Integer.class.getName(), "mule:substitutableInt");
        mapping.put("long", "mule:substitutableLong");
        mapping.put(Long.class.getName(), "mule:substitutableLong");
        mapping.put(String.class.getName(), "mule:substitutableName");
        mapping.put(Class.class.getName(), "mule:substitutableClass");

        TYPES_MAP = Collections.unmodifiableMap(mapping);
    }

    public String getXmlType(boolean isConfig)
    {
        if( isEnum() )
            return getName() + "Enum";

        if( !isConfig )
            return "xsd:string";

        return TYPES_MAP.get(getName());
    }

    public boolean isVoid()
    {
        return "void".equals(getName());
    }
}
