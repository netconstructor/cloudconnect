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

import org.mule.tools.cloudconnect.model.JavaModel;
import org.mule.tools.cloudconnect.parser.qdox.QDoxModel;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
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
        QDoxModel model = new QDoxModel();
        List<File> sourceRoots = new ArrayList<File>();
        for (String sourceRoot : (List<String>) project.getCompileSourceRoots())
        {
            sourceRoots.add(new File(sourceRoot));
        }

        InputStream input = null;
        model.parse(sourceRoots);
        IOUtil.close(input);

        return model;
    }

    protected void createAndAttachGeneratedSourcesDirectory() throws MojoExecutionException
    {
        File sourceDirectory = generatedSourcesDirectory();
        createDirectory(sourceDirectory);
        getProject().addCompileSourceRoot(sourceDirectory.getAbsolutePath());

        File resourceDirectory = generatedResourcesDirectory();
        Resource generatedResources = new Resource();
        generatedResources.setDirectory(resourceDirectory.getAbsolutePath());
        getProject().addResource(generatedResources);
    }

    protected File generatedSourcesDirectory()
    {
        return new File(getTargetDirectory(), "generated-sources/mule");
    }

    protected File generatedResourcesDirectory()
    {
        return new File(getTargetDirectory(), "generated-resources/mule");
    }
}
