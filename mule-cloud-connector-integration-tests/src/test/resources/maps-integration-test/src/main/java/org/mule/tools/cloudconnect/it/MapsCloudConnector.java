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

package org.mule.tools.cloudconnect.it;

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

import java.util.Map;

@Connector(namespacePrefix="maps")
public class MapsCloudConnector
{
    public enum Color
    {
        Red(2),
        Green(4),
        Blue(8);

        private int value;

        Color(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return this.value;
        }
    };

	@Operation
    public int countKeys(Map<String, String> strings)
    {
		return strings.keySet().size();
    }

    @Operation
    public int countKeysWithEnum(Map<Color, String> colors)
    {
		return colors.keySet().size();
    }

    @Operation
    public String getFirstValue(Map<String, String> strings)
    {
		return strings.entrySet().iterator().next().getValue();
    }

    @Operation
    public String getFirstKey(Map<String, String> strings)
    {
		return strings.entrySet().iterator().next().getKey();
    }
}