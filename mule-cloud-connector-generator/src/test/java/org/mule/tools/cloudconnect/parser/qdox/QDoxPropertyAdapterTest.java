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

package org.mule.tools.cloudconnect.parser.qdox;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.Type;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QDoxPropertyAdapterTest
{

    private static final String NAME = "NAME";
    private static final String TYPE = "TYPE";

    @Test
    public void name() throws Exception
    {
        BeanProperty property = mock(BeanProperty.class);
        JavaField field = mock(JavaField.class);
        when(property.getName()).thenReturn(NAME);

        QDoxPropertyAdapter propertyAdapter = new QDoxPropertyAdapter(property, field);

        assertEquals(NAME, propertyAdapter.getName());
    }
}
