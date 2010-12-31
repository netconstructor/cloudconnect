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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SchemaGeneratorTestCase
{
    private SchemaGenerator generator;

    @Before
    public void createSchemaGenerator()
    {
        generator = new SchemaGenerator();
        generator.setNamespaceIdentifierSuffix("test-schema");
        generator.setSchemaVersion("3.1");
    }

    @Test(expected = IllegalStateException.class)
    public void missingNamespaceIdentifierSuffix() throws Exception
    {
        generator.setNamespaceIdentifierSuffix(null);
        generator.generate(new ByteArrayOutputStream());
    }

    @Test(expected = IllegalStateException.class)
    public void missingSchemaVersion() throws Exception
    {
        generator.setSchemaVersion(null);
        generator.generate(new ByteArrayOutputStream());
    }

    @Test
    public void schemaSkeleton() throws Exception
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream(1500);
        generator.generate(output);

        InputStream sourceInput = new ByteArrayInputStream(output.toByteArray());
        InputStream controlInput = getTestResource("schema-skeleton.xsd");
        assertTrue(IOUtils.contentEquals(sourceInput, controlInput));
    }

    private InputStream getTestResource(String filename)
    {
        InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
        assertNotNull(input);
        return input;
    }
}
