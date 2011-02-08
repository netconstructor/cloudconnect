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

import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NamespaceHandlerGeneratorTest
{

    private static final String CLASS_NAME = "SOME_CLASS_NAME";
    private static final String PACKAGE_NAME = "SOME_PACKAGE_NAME";
    private static final String JAVA_CLASS_MODEL_KEY = "class";
    private static final String CLASS_NAME_MODEL_KEY = "className";
    private static final String PACAKGE_NAME_MODEL_KEY = "packageName";

    @Test
    public void verifyModel() throws Exception
    {
        JavaClass javaClass = mock(JavaClass.class);
        when(javaClass.getName()).thenReturn(CLASS_NAME);

        NamespaceHandlerGenerator generator = new NamespaceHandlerGenerator();
        generator.setClassName(CLASS_NAME);
        generator.setPackageName(PACKAGE_NAME);
        generator.setJavaClass(javaClass);

        Map<String, Object> model = generator.createModel();

        assertTrue(CLASS_NAME.equals(((JavaClass) model.get(JAVA_CLASS_MODEL_KEY)).getName()));
        assertTrue(CLASS_NAME.equals(((String) model.get(CLASS_NAME_MODEL_KEY))));
        assertTrue(PACKAGE_NAME.equals(((String) model.get(PACAKGE_NAME_MODEL_KEY))));
    }
}
