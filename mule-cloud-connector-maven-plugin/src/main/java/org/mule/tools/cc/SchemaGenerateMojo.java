/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cc;

import org.mule.tools.cc.generator.SchemaGenerator;
import org.mule.tools.cc.generator.SpringSchemaGenerator;
import org.mule.tools.cc.model.JavaClass;

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

    /**
     * @parameter
     * @required
     */
    private String muleVersion;

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        createAndAttachGeneratedResourcesDirectory();

        // setup source roots
        for (String sourceRoot : (List<String>) getProject().getCompileSourceRoots())
        {
            getParser().addSourceTree( new File(sourceRoot));
        }

        for (Connector c : getConnectors())
        {

            String suffix = determineNamespaceIdentifierSuffixFromSchemaFilename(c.getSchemaFilename());
            String schemaVersion = determineSchemaVersionFromMuleVersion();

            JavaClass javaClass = parseCloudConnectorClass(c.getCloudConnectorClass());

            SchemaGenerator generator = new SchemaGenerator();
            generator.setJavaClass(javaClass);
            generator.setNamespaceIdentifierSuffix(suffix);
            generator.setSchemaVersion(schemaVersion);

            SpringSchemaGenerator springSchemaGenerator = new SpringSchemaGenerator();
            springSchemaGenerator.setNamespaceIdentifierSuffix(suffix);

            OutputStream output = null;
            try
            {
                output = openSchemaFileStream(c.getSchemaFilename());
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

    private String determineSchemaVersionFromMuleVersion()
    {
        return muleVersion.substring(0, 3);
    }


    private void createAndAttachGeneratedResourcesDirectory() throws MojoExecutionException
    {
        File resourceDirectory = generatedResourcesDirectory();
        createDirectory(resourceDirectory);
        getProjectHelper().addResource(project, resourceDirectory.getAbsolutePath(), null, null);
    }

    private OutputStream openSchemaFileStream(String schemaFilename) throws IOException, MojoExecutionException
    {
        File metaInfDirectory = new File(generatedResourcesDirectory(), "META-INF");
        createDirectory(metaInfDirectory);

        File schemaFile = new File(metaInfDirectory, schemaFilename);
        return new FileOutputStream(schemaFile);
    }

    private OutputStream openSpringSchemaFileStream() throws IOException, MojoExecutionException
    {
        File metaInfDirectory = new File(generatedResourcesDirectory(), "META-INF");
        createDirectory(metaInfDirectory);

        File springSchemaFile = new File(metaInfDirectory, "spring.schemas");
        return new FileOutputStream(springSchemaFile);
    }

}
