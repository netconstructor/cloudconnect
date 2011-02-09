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

package org.mule.tools.cloudconnect;

import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaModel;
import org.mule.tools.cloudconnect.parser.ClassParseException;
import org.mule.tools.cloudconnect.parser.ClassParser;
import org.mule.tools.cloudconnect.parser.qdox.QDoxClassParser;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.util.IOUtil;

public abstract class AbstractConnectorMojo extends AbstractMojo
{
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

    /**
     * @parameter expression="${project.version}"
     * @readonly
     */
    protected String schemaVersion;

    /**
     *
     */
    private ClassParser parser;

    public AbstractConnectorMojo()
    {
        parser = new QDoxClassParser();
        parser.setLog(new MavenClassParserLog(getLog()));
    }

    public MavenProjectHelper getProjectHelper()
    {
        return projectHelper;
    }

    public MavenProject getProject()
    {
        return project;
    }

    public File getTargetDirectory()
    {
        return targetDirectory;
    }

    protected void createDirectory(File directory) throws MojoExecutionException
    {
        if (directory.exists() == false)
        {
            if (directory.mkdirs() == false)
            {
                throw new MojoExecutionException("Could not create " + directory.getAbsolutePath());
            }
        }
    }

    protected JavaModel parseModel() throws MojoExecutionException
    {
        for (String sourceRoot : (List<String>) project.getCompileSourceRoots())
        {
            parser.addSourceTree(new File(sourceRoot));
        }

        InputStream input = null;
        try
        {
            return parser.parse();
        }
        catch (ClassParseException cpe)
        {
            throw new MojoExecutionException(
                    "Error while parsing ", cpe);
        }
        finally
        {
            IOUtil.close(input);
        }
    }

    public ClassParser getParser()
    {
        return parser;
    }

    protected String determineNamespaceIdentifierSuffixFromSchemaFilename(String schemaFilename) throws MojoExecutionException
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

    protected void createAndAttachGeneratedSourcesDirectory() throws MojoExecutionException
    {
        File sourceDirectory = generatedSourcesDirectory();
        createDirectory(sourceDirectory);
        getProject().addCompileSourceRoot(sourceDirectory.getAbsolutePath());

        File resourceDirectory = generatedResourcesDirectory();
        Resource testResource = new Resource();
        testResource.setDirectory(resourceDirectory.getAbsolutePath());
        getProject().addTestResource(testResource);

    }

    protected File generatedSourcesDirectory()
    {
        return new File(getTargetDirectory(), "generated-sources/mule");
    }

    protected File generatedResourcesDirectory()
    {
        return new File(getTargetDirectory(), "generated-resources/mule");
    }

    protected String getSchemaVersion()
    {
        return schemaVersion;
    }
}
