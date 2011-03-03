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

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Redefinable;

import java.util.List;

@Connector(namespacePrefix="arrays")
public class ArraysCloudConnector
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
    public int sumAll(int[] numbers)
    {
        int result = 0;

		for( int i = 0; i < numbers.length; i++ )
		{
			result += numbers[i];
		}
		
		return result;
    }

    @Operation(name="sumAllFromGenericList")
    public int sumAll(List<Integer> numbers)
    {
        int result = 0;

        for( Integer x : numbers )
        {
            result += x.intValue();
        }

		return result;
    }

    @Operation
    public int sumAllColors(List<Color> colors)
    {
        int result = 0;

        for( Color x : colors )
        {
            result += x.getValue();
        }

		return result;
    }
}