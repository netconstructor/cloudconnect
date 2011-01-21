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

import com.thoughtworks.qdox.model.JavaClass;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.util.IOUtil;

import java.io.*;

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
     * Absolute path to the generated namespace handler source file.
     *
     * @parameter
     * @required
     */
    private File namespaceHandler;

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

        JavaClass javaClass = parseCloudConnectorClass();
        runSchemaGenerator(javaClass);
        runNamespaceHandlerGenerator(javaClass);
    }

    private void createAndAttachOutputDirectories() throws MojoExecutionException
    {
        createAndAttachGeneratedResourcesDirectory();
        createAndAttachGeneratedSourcesDirectory();
    }

    private void createAndAttachGeneratedResourcesDirectory() throws MojoExecutionException
    {
        File resourceDirectory = generatedResourcesDirectory();
        createDirectory(resourceDirectory);
        projectHelper.addResource(project, resourceDirectory.getAbsolutePath(), null, null);
    }

    private void createAndAttachGeneratedSourcesDirectory() throws MojoExecutionException
    {
        File sourceDirectory = generatedSourcesDirectory();
        createDirectory(sourceDirectory);
        project.addCompileSourceRoot(sourceDirectory.getAbsolutePath());
    }

    private void runSchemaGenerator(JavaClass javaClass) throws MojoExecutionException
    {
        FreeMarkerSchemaGenerator generator = new FreeMarkerSchemaGenerator();
        generator.setJavaClass(javaClass);

        String suffix = determineNamespaceIdentifierSuffixFromSchemaFilename();
        generator.setNamespaceIdentifierSuffix(suffix);

        String schemaVersion = determineSchemaVersionFromMuleVersion();
        generator.setSchemaVersion(schemaVersion);

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
        catch (IllegalArgumentException iax)
        {
            throw new MojoExecutionException(
                "Error while parsing " + cloudConnector.getAbsolutePath(), iax);
        }
        finally
        {
            IOUtil.close(input);
        }
    }

    private void runGenerator(FreeMarkerSchemaGenerator generator) throws MojoExecutionException
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

    private void runNamespaceHandlerGenerator(JavaClass javaClass) throws MojoExecutionException
    {
        NamespaceHandlerGenerator generator = new NamespaceHandlerGenerator();
        generator.setJavaClass(javaClass);

        String packageName = determinePackageNameFromNamespaceHandlerFile();
        generator.setPackageName(packageName);

        String className = determineClassNameFromNamespaceHandlerFile();
        generator.setClassName(className);

        runGenerator(generator);
    }

    private String determinePackageNameFromNamespaceHandlerFile()
    {
        String namespaceHandlerPath = namespaceHandler.getAbsolutePath();

        // cut off the absolute path to the project
        String basedir = project.getBasedir().getAbsolutePath();
        String relativeNamespaceHandlerPath = namespaceHandlerPath.replace(basedir, "");

        // cut off the leading '/'
        relativeNamespaceHandlerPath = relativeNamespaceHandlerPath.substring(1);

        String packageName = namespaceHandlerProjectRelativeFile().getParent();
        packageName = packageName.replace('/', '.');    // unix paths
        packageName = packageName.replace('\\', '.');   // windows paths

        return packageName;
    }

    private File namespaceHandlerProjectRelativeFile()
    {
        String namespaceHandlerPath = namespaceHandler.getAbsolutePath();

        // cut off the absolute path to the project
        String basedir = project.getBasedir().getAbsolutePath();
        String relativeNamespaceHandlerPath = namespaceHandlerPath.replace(basedir, "");

        // cut off the leading '/'
        relativeNamespaceHandlerPath = relativeNamespaceHandlerPath.substring(1);

        return new File(relativeNamespaceHandlerPath);
    }

    private String determineClassNameFromNamespaceHandlerFile()
    {
        String className = namespaceHandler.getName();
        return className.replace(".java", "");
    }

    private void runGenerator(NamespaceHandlerGenerator generator) throws MojoExecutionException
    {
        OutputStream output = null;
        try
        {
            output = openNamespaceHandlerFileStream();
            generator.generate(output);
        }
        catch (IOException iox)
        {
            throw new MojoExecutionException("Error while generating namespace handler", iox);
        }
        finally
        {
            IOUtil.close(output);
        }
    }

    private OutputStream openNamespaceHandlerFileStream() throws IOException, MojoExecutionException
    {
        String packageDir = namespaceHandlerProjectRelativeFile().getParentFile().getPath();
        File absolutePackageDir = new File(generatedSourcesDirectory(), packageDir);
        createDirectory(absolutePackageDir);

        File namespaceJavaFile = new File(generatedSourcesDirectory(),
            namespaceHandlerProjectRelativeFile().getPath());
        return new FileOutputStream(namespaceJavaFile);
    }

    private File generatedResourcesDirectory()
    {
        return new File(targetDirectory, "generated-resources/mule");
    }

    private File generatedSourcesDirectory()
    {
        return new File(targetDirectory, "generated-sources/mule");
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
