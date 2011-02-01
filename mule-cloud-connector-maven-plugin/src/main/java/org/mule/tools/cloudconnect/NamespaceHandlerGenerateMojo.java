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

import org.mule.tools.cloudconnect.generator.NamespaceHandlerGenerator;
import org.mule.tools.cloudconnect.generator.SpringNamespaceHandlerGenerator;
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
 * @phase generate-sources
 * @goal namespace-handler-generate
 */
public class NamespaceHandlerGenerateMojo extends AbstractConnectorMojo
{


    public void execute() throws MojoExecutionException, MojoFailureException
    {
        createAndAttachGeneratedSourcesDirectory();

        JavaModel model = parseModel();
        for (JavaClass javaClass : model.getClasses())
        {
            if (javaClass.isConnector())
            {
                String namespaceHandlerPackage = javaClass.getPackage() + ".config";
                String namesapceHandlerName = javaClass.getName() + "NamespaceHandler";

                NamespaceHandlerGenerator generator = new NamespaceHandlerGenerator();
                generator.setJavaClass(javaClass);
                generator.setPackageName(namespaceHandlerPackage);
                generator.setClassName(namesapceHandlerName);

                OutputStream output = null;
                try
                {
                    output = openNamespaceHandlerFileStream(namespaceHandlerPackage, namesapceHandlerName);
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

                SpringNamespaceHandlerGenerator springNamespaceHandlerGenerator = new SpringNamespaceHandlerGenerator();
                springNamespaceHandlerGenerator.setJavaClass(javaClass);
                springNamespaceHandlerGenerator.setPackageName(namespaceHandlerPackage);
                springNamespaceHandlerGenerator.setClassName(namesapceHandlerName);

                try
                {
                    output = openSpringNamespaceHandlerFileStream();
                    springNamespaceHandlerGenerator.generate(output);
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

    private void createAndAttachGeneratedSourcesDirectory() throws MojoExecutionException
    {
        File sourceDirectory = generatedSourcesDirectory();
        createDirectory(sourceDirectory);
        getProject().addCompileSourceRoot(sourceDirectory.getAbsolutePath());
    }

    private File generatedSourcesDirectory()
    {
        return new File(getTargetDirectory(), "generated-sources/mule");
    }

    private File namespaceHandlerProjectRelativeFile(File namespaceHandler)
    {
        String namespaceHandlerPath = namespaceHandler.getAbsolutePath();

        // cut off the absolute path to the project
        String basedir = project.getBasedir().getAbsolutePath();
        String relativeNamespaceHandlerPath = namespaceHandlerPath.replace(basedir, "");

        // cut off the leading '/'
        relativeNamespaceHandlerPath = relativeNamespaceHandlerPath.substring(1);

        return new File(relativeNamespaceHandlerPath);
    }

    private OutputStream openNamespaceHandlerFileStream(String namespaceHandlerPackage, String namesapceHandlerName) throws IOException, MojoExecutionException
    {
        String directory = namespaceHandlerPackage.replace('.', File.separatorChar);
        File namespaceDirectory = new File(generatedSourcesDirectory(), directory);
        createDirectory(namespaceDirectory);

        File namespaceHandlerFile = new File(namespaceDirectory, namesapceHandlerName + ".java");
        return new FileOutputStream(namespaceHandlerFile);
    }

    private OutputStream openSpringNamespaceHandlerFileStream() throws IOException, MojoExecutionException
    {
        File metaInfDirectory = new File(generatedResourcesDirectory(), "META-INF");
        createDirectory(metaInfDirectory);

        File springSchemaFile = new File(metaInfDirectory, "spring.handlers");
        return new FileOutputStream(springSchemaFile);
    }


}
