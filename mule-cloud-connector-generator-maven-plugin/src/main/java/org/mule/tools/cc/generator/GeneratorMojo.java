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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.util.IOUtil;

/**
 * Generate schema for a Mule cloud connector.
 *
 * @phase generate-sources
 * @goal generate
 */
public class GeneratorMojo extends AbstractMojo
{
    /**
     * Absolute path to the Java source file of the cloud connector class.
     *
     * @parameter
     * @required
     */
    private File cloudConnector;

    /**
     * Filename of the generated schema file.
     *
     * @parameter
     * @required
     */
    private String schemaFilename;

    /**
     * @parameter
     * @required
     */
    private String muleVersion;

    /**
     * Directory containing the classes.
     *
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private File targetDirectory;

    /**
     * @component
     */
    private MavenProjectHelper projectHelper;

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    protected MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        createAndAttachOutputDirectories();
        runSchemaGenerator();
    }

    private void createAndAttachOutputDirectories() throws MojoExecutionException
    {
        File resourceDirectory = generatedResourcesDirectory();
        createDirectory(resourceDirectory);
        projectHelper.addResource(project, resourceDirectory.getAbsolutePath(), null, null);
    }

    private void runSchemaGenerator() throws MojoExecutionException
    {
        SchemaGenerator generator = new SchemaGenerator();

        String suffix = determineNamespaceIdentifierSuffixFromSchemaFilename();
        generator.setNamespaceIdentifierSuffix(suffix);

        String schemaVersion = determineSchemaVersionFromMuleVersion();
        generator.setSchemaVersion(schemaVersion);

        JavaClass javaClass = parseCloudConnectorClass();
        generator.setJavaClass(javaClass);

        runGenerator(generator);
    }

    private String determineNamespaceIdentifierSuffixFromSchemaFilename() throws MojoExecutionException
    {
        if (schemaFilename.startsWith("mule-") == false)
        {
            throw new MojoExecutionException("schemaFilename must start with 'mule-'");
        }
        if (schemaFilename.endsWith(".xsd") == false)
        {
            throw new MojoExecutionException("schemaFilename must end with the .xsd extension");
        }

        String suffix = schemaFilename.replace("mule-", "");
        suffix = suffix.replace(".xsd", "");
        return suffix;
    }

    private String determineSchemaVersionFromMuleVersion()
    {
        return muleVersion.substring(0, 3);
    }

    private JavaClass parseCloudConnectorClass() throws MojoExecutionException
    {
        if (cloudConnector.exists() == false)
        {
            throw new MojoExecutionException(cloudConnector.getAbsolutePath() + " does not exist");
        }

        InputStream input = null;
        try
        {
            input = new FileInputStream(cloudConnector);
            return new JavaClassParser().parse(input);
        }
        catch (IOException iox)
        {
            throw new MojoExecutionException(
                "Error while parsing " + cloudConnector.getAbsolutePath(), iox);
        }
        finally
        {
            IOUtil.close(input);
        }
    }

    private void runGenerator(SchemaGenerator generator) throws MojoExecutionException
    {
        OutputStream output = null;
        try
        {
            output = openSchemaFileStream();
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
    }

    private OutputStream openSchemaFileStream() throws IOException, MojoExecutionException
    {
        File metaInfDirectory = new File(generatedResourcesDirectory(), "META-INF");
        createDirectory(metaInfDirectory);

        File schemaFile = new File(metaInfDirectory, schemaFilename);
        return new FileOutputStream(schemaFile);
    }

    private File generatedResourcesDirectory()
    {
        return new File(targetDirectory, "generated-resources/mule");
    }

    private void createDirectory(File directory) throws MojoExecutionException
    {
        if (directory.exists() == false)
        {
            if (directory.mkdirs() == false)
            {
                throw new MojoExecutionException("Could not create " + directory.getAbsolutePath());
            }
        }
    }
}
