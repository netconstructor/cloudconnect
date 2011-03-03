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

package org.mule.tools.cloudconnect;

import org.mule.api.annotations.ContainsTransformerMethods;
import org.mule.api.annotations.Transformer;
import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

@ContainsTransformerMethods
@Connector(namespacePrefix = "basic")
public class BasicCloudConnector
{

    @Operation
    public char passthruChar(char value)
    {
        return value;
    }

    @Operation
    public String passthruString(String value)
    {
        return value;
    }

    @Operation
    public float passthruFloat(float value)
    {
        return value;
    }

    @Operation
    public boolean passthruBoolean(boolean value)
    {
        return value;
    }

    @Operation
    public int passthruInteger(int value)
    {
        return value;
    }

    @Operation
    public long passthruLong(long value)
    {
        return value;
    }

    @Operation
    public Float passthruComplexFloat(Float value)
    {
        return value;
    }

    @Operation
    public Boolean passthruComplexBoolean(Boolean value)
    {
        return value;
    }

    @Operation
    public Integer passthruComplexInteger(Integer value)
    {
        return value;
    }

    @Operation
    public Long passthruComplexLong(Long value)
    {
        return value;
    }

    @Transformer(sourceTypes = {String.class})
    public char transform(String str)
    {
        return str.charAt(0);
    }

}
