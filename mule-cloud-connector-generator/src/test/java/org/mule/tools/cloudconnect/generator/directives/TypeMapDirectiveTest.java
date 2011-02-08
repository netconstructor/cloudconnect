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

package org.mule.tools.cloudconnect.generator.directives;

import freemarker.template.TemplateDirectiveModel;
import org.junit.Test;

public class TypeMapDirectiveTest extends AbstractDirectiveTest
{

    @Override
    protected TemplateDirectiveModel createDirective()
    {
        return new TypeMapDirective();
    }

    @Test
    public void integer() throws Exception
    {
        testDirective("int", "xsd:integer");
    }

    @Test
    public void date() throws Exception
    {
        testDirective("java.lang.Date", "xsd:date");
    }

    @Test
    public void str() throws Exception
    {
        testDirective("java.lang.String", "xsd:string");
    }

    @Test
    public void bool() throws Exception
    {
        testDirective("boolean", "xsd:boolean");
    }

    @Test
    public void boolClass() throws Exception
    {
        testDirective("java.lang.Boolean", "xsd:boolean");
    }

    @Test
    public void integerClass() throws Exception
    {
        testDirective("java.lang.Integer", "xsd:integer");
    }

}
