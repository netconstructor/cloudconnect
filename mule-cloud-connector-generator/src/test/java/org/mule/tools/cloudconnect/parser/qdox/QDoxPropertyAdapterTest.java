/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cloudconnect.parser.qdox;

import com.thoughtworks.qdox.model.BeanProperty;
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
        when(property.getName()).thenReturn(NAME);

        QDoxPropertyAdapter propertyAdapter = new QDoxPropertyAdapter(property);

        assertEquals(NAME, propertyAdapter.getName());
    }

    @Test
    public void type() throws Exception
    {
        Type type = mock(Type.class);
        when(type.getValue()).thenReturn(TYPE);
        BeanProperty property = mock(BeanProperty.class);
        when(property.getType()).thenReturn(type);

        QDoxPropertyAdapter propertyAdapter = new QDoxPropertyAdapter(property);

        assertEquals(TYPE, propertyAdapter.getType());
    }

}
