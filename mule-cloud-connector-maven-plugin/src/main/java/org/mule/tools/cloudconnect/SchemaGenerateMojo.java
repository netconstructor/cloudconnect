/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cloudconnect;

import org.mule.tools.cloudconnect.generator.SchemaGenerator;
import org.mule.tools.cloudconnect.generator.SpringSchemaGenerator;
import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.IOUtil;

/**
 * @phase generate-resources
 * @goal schema-generate
 */
public class SchemaGenerateMojo extends AbstractConnectorMojo
{
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        createAndAttachGeneratedSourcesDirectory();

        JavaModel model = parseModel();
        for (JavaClass javaClass : model.getClasses())
        {
            if (javaClass.isConnector())
            {

                SchemaGenerator generator = new SchemaGenerator();
                generator.setJavaClass(javaClass);

                OutputStream output = null;
                try
                {
                    output = openSchemaFileStream("mule-" + javaClass.getNamespacePrefix() + ".xsd");
                    generator.generate(output);
                }
                catch (IOException iox)
                {
                    throw new MojoExecutionException("Error while generating schema", iox);
                }
                finally
                {
                    IOUtil.close(output);
                }

                SpringSchemaGenerator springSchemaGenerator = new SpringSchemaGenerator();
                springSchemaGenerator.setJavaClass(javaClass);

                try
                {
                    output = openSpringSchemaFileStream();
                    springSchemaGenerator.generate(output);
                }
                catch (IOException iox)
                {
                    throw new MojoExecutionException("Error while generating schema", iox);
                }
                finally
                {
                    IOUtil.close(output);
                }
            }
        }
    }

    private OutputStream openSchemaFileStream(String schemaFilename) throws IOException, MojoExecutionException
    {
        File metaInfDirectory = getResourcesMetaInf();

        File schemaFile = new File(metaInfDirectory, schemaFilename);
        return new FileOutputStream(schemaFile);
    }

    private OutputStream openSpringSchemaFileStream() throws IOException, MojoExecutionException
    {
        File metaInfDirectory = getResourcesMetaInf();

        File springSchemaFile = new File(metaInfDirectory, "spring.schemas");
        return new FileOutputStream(springSchemaFile);
    }

    private File getResourcesMetaInf()
            throws MojoExecutionException
    {
        File metaInfDirectory = new File(generatedSourcesDirectory(), "META-INF");
        createDirectory(metaInfDirectory);
        return metaInfDirectory;
    }

}
