/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cc.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SchemaTypesMapping
{
    private static final Map<String, String> TYPES_MAP;

    static
    {
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put("boolean", "xsd:boolean");
        mapping.put("Boolean", "xsd:boolean");
        mapping.put("int", "xsd:integer");
        mapping.put("Integer", "xsd:integer");
        mapping.put("String", "xsd:string");

        TYPES_MAP = Collections.unmodifiableMap(mapping);
    }

    public static String schemaTypeForJavaTypeName(String javaTypeName)
    {
        String schemaType = TYPES_MAP.get(javaTypeName);
        if (schemaType == null)
        {
            String message = String.format("Don't know how to map from Java type '%1s' to schema type", javaTypeName);
            throw new IllegalStateException(message);
        }
        return schemaType;
    }
}
