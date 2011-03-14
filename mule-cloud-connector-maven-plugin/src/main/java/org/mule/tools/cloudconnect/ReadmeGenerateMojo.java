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

import org.mule.tools.cloudconnect.generator.ReadmeGenerator;
import org.mule.tools.cloudconnect.generator.SchemaGenerator;
import org.mule.tools.cloudconnect.generator.SpringSchemaGenerator;
import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.IOUtil;

/**
 * @goal readme-generate
 */
public class ReadmeGenerateMojo extends AbstractConnectorMojo
{

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        createAndAttachGeneratedSourcesDirectory();

        JavaModel model = parseModel();
        List<JavaClass> classes = new ArrayList<JavaClass>();
        OutputStream output = null;

        ReadmeGenerator readmeGenerator = new ReadmeGenerator();
        readmeGenerator.setName(project.getName());
        readmeGenerator.setDescription(project.getDescription());
        readmeGenerator.setArtifactId(project.getArtifactId());
        readmeGenerator.setGroupId(project.getGroupId());
        readmeGenerator.setVersion(project.getVersion());

        if( project.getDistributionManagement() == null ||
            project.getDistributionManagement().getRepository() == null )
        {
            throw new MojoExecutionException("No distribution management section in POM");
        }

        readmeGenerator.setRepoId(project.getDistributionManagement().getRepository().getId());
        readmeGenerator.setRepoName(project.getDistributionManagement().getRepository().getName());
        readmeGenerator.setRepoUrl(project.getDistributionManagement().getRepository().getUrl().replace("dav:", ""));
        readmeGenerator.setRepoLayout(project.getDistributionManagement().getRepository().getLayout());

        for (JavaClass javaClass : model.getClasses())
        {
            if (javaClass.isConnector())
            {
                readmeGenerator.setJavaClass(javaClass);
                break;
            }
        }

        try
        {
            output = openReadmeFilestream("README.md");
            readmeGenerator.generate(output);
        }
        catch (IOException iox)
        {
            throw new MojoExecutionException("Error while generating readme", iox);
        }
        finally
        {
            IOUtil.close(output);
        }
    }

    private OutputStream openReadmeFilestream(String readmeFilename) throws IOException, MojoExecutionException
    {
        File schemaFile = new File(project.getBasedir(), readmeFilename);
        return new FileOutputStream(schemaFile);
    }
}
