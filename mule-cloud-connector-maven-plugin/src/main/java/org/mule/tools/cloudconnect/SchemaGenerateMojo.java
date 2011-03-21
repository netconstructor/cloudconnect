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
 * @phase generate-resources
 * @goal schema-generate
 */
public class SchemaGenerateMojo extends AbstractConnectorMojo
{
    /**
     * Directory under which the schema will be generated.
     *
     * @parameter expression="${basedir}/src/main/resources"
     */
    private File schemaDirectory;


    public void execute() throws MojoExecutionException, MojoFailureException
    {
        createAndAttachGeneratedSourcesDirectory();

        JavaModel model = parseModel();
        List<JavaClass> classes = new ArrayList<JavaClass>();
        OutputStream output = null;
        for (JavaClass javaClass : model.getClasses())
        {
            if (javaClass.isConnector())
            {
                classes.add(javaClass);

                SchemaGenerator generator = new SchemaGenerator();
                generator.setJavaClass(javaClass);

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

            }

            SpringSchemaGenerator springSchemaGenerator = new SpringSchemaGenerator();
            springSchemaGenerator.setClasses(classes);
            springSchemaGenerator.setSchemaVersion(getSchemaVersion());

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

    private OutputStream openSchemaFileStream(String schemaFilename) throws IOException, MojoExecutionException
    {
        File metaInfDirectory = getSourceResourcesMetaInf();

        File schemaFile = new File(metaInfDirectory, schemaFilename);
        return new FileOutputStream(schemaFile);
    }

    private OutputStream openSpringSchemaFileStream() throws IOException, MojoExecutionException
    {
        File metaInfDirectory = getResourcesMetaInf();

        File springSchemaFile = new File(metaInfDirectory, "spring.schemas");
        return new FileOutputStream(springSchemaFile);
    }

    private File getSourceResourcesMetaInf()
            throws MojoExecutionException
    {
        File metaInfDirectory = new File(schemaDirectory, "META-INF");
        createDirectory(metaInfDirectory);
        return metaInfDirectory;
    }

    private File getResourcesMetaInf()
            throws MojoExecutionException
    {
        File metaInfDirectory = new File(generatedResourcesDirectory(), "META-INF");
        createDirectory(metaInfDirectory);
        return metaInfDirectory;
    }

}
