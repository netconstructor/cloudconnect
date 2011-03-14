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
import org.mule.tools.cloudconnect.annotations.Parameter;
import org.mule.tools.cloudconnect.annotations.Property;

import java.util.Map;

@Connector(namespacePrefix="opt")
public class OptionalCloudConnector
{
    @Property(optional=true)
    private String url;

    @Operation
    public int sumAndMultiply(int a, int b, @Parameter(optional=true, defaultValue="1") int c)
    {
        return (a + b) * c;
    }

    @Operation
    public int count(@Parameter(optional=true) Map<String, String> fields)
    {
        if( fields == null )
        {
            return 0;
        }

        return fields.size();
    }


    public void setUrl(String url)
    {
        this.url = url;
    }
}
