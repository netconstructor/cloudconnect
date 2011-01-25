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

import org.mule.tools.cc.generator.NamespaceHandlerGenerator;
import org.mule.tools.cc.model.JavaClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

        for (Connector c : getConnectors())
        {
            JavaClass javaClass = parseCloudConnectorClass(c.getCloudConnectorClass());

            NamespaceHandlerGenerator generator = new NamespaceHandlerGenerator();
            generator.setJavaClass(javaClass);

            String packageName = determinePackageNameFromNamespaceHandlerFile(c.getNamespaceHandler());
            generator.setPackageName(packageName);

            String className = determineClassNameFromNamespaceHandlerFile(c.getNamespaceHandler());
            generator.setClassName(className);

            OutputStream output = null;
            try
            {
                output = openNamespaceHandlerFileStream(c.getNamespaceHandler());
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

    private String determinePackageNameFromNamespaceHandlerFile(File namespaceHandler)
    {
        String namespaceHandlerPath = namespaceHandler.getAbsolutePath();

        // cut off the absolute path to the project
        String basedir = project.getBasedir().getAbsolutePath();
        String relativeNamespaceHandlerPath = namespaceHandlerPath.replace(basedir, "");

        // cut off the leading '/'
        relativeNamespaceHandlerPath = relativeNamespaceHandlerPath.substring(1);

        String packageName = namespaceHandlerProjectRelativeFile(namespaceHandler).getParent();
        packageName = packageName.replace('/', '.');    // unix paths
        packageName = packageName.replace('\\', '.');   // windows paths

        return packageName;
    }

    private String determineClassNameFromNamespaceHandlerFile(File namespaceHandler)
    {
        String className = namespaceHandler.getName();
        return className.replace(".java", "");
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

    private OutputStream openNamespaceHandlerFileStream(File namespaceHandler) throws IOException, MojoExecutionException
    {
        String packageDir = namespaceHandlerProjectRelativeFile(namespaceHandler).getParentFile().getPath();
        File absolutePackageDir = new File(generatedSourcesDirectory(), packageDir);
        createDirectory(absolutePackageDir);

        File namespaceJavaFile = new File(generatedSourcesDirectory(),
                                          namespaceHandlerProjectRelativeFile(namespaceHandler).getPath());
        return new FileOutputStream(namespaceJavaFile);
    }

}
