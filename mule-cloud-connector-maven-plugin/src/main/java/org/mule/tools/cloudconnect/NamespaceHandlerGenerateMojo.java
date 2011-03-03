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

import org.mule.tools.cloudconnect.generator.BeanDefinitionParserGenerator;
import org.mule.tools.cloudconnect.generator.EnumTransformerGenerator;
import org.mule.tools.cloudconnect.generator.MessageProcessorGenerator;
import org.mule.tools.cloudconnect.generator.NamespaceHandlerGenerator;
import org.mule.tools.cloudconnect.generator.RegistryBootstrapGenerator;
import org.mule.tools.cloudconnect.generator.SpringNamespaceHandlerGenerator;
import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaMethod;
import org.mule.tools.cloudconnect.model.JavaModel;
import org.mule.tools.cloudconnect.model.JavaType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<JavaClass> classes = new ArrayList<JavaClass>();
        Set<JavaType> enums = new HashSet<JavaType>();
        for (JavaClass javaClass : model.getClasses())
        {
            if (javaClass.isConnector())
            {
                classes.add(javaClass);
                enums.addAll(javaClass.getEnums());

                NamespaceHandlerGenerator generator = new NamespaceHandlerGenerator();
                generator.setJavaClass(javaClass);

                OutputStream output = null;
                try
                {
                    output = openNamespaceHandlerFileStream(javaClass.getNamespaceHandlerPackage(), javaClass.getNamespaceHandlerName());
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

                BeanDefinitionParserGenerator beanDefinitionParserGenerator = new BeanDefinitionParserGenerator();
                beanDefinitionParserGenerator.setJavaClass(javaClass);
                MessageProcessorGenerator messageProcessorGenerator = new MessageProcessorGenerator();
                messageProcessorGenerator.setJavaClass(javaClass);

                for (JavaMethod method : javaClass.getMethods())
                {
                    if (!method.isOperation())
                    {
                        continue;
                    }

                    beanDefinitionParserGenerator.setJavaMethod(method);

                    try
                    {
                        output = openNamespaceHandlerFileStream(javaClass.getNamespaceHandlerPackage(), method.getBeanDefinitionParserName());
                        beanDefinitionParserGenerator.generate(output);
                    }
                    catch (IOException iox)
                    {
                        throw new MojoExecutionException("Error while generating schema", iox);
                    }
                    finally
                    {
                        IOUtil.close(output);
                    }

                    messageProcessorGenerator.setJavaMethod(method);

                    try
                    {
                        output = openNamespaceHandlerFileStream(javaClass.getNamespaceHandlerPackage(), method.getMessageProcessorName());
                        messageProcessorGenerator.generate(output);
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

                EnumTransformerGenerator enumTransformerGenerator = new EnumTransformerGenerator();

                for (JavaType enumType : javaClass.getEnums())
                {
                    enumTransformerGenerator.setJavaType(enumType);

                    try
                    {
                        output = openNamespaceHandlerFileStream(enumType.getTransformerPackage(), enumType.getTransformerName());
                        enumTransformerGenerator.generate(output);
                    }
                    catch (IOException iox)
                    {
                        throw new MojoExecutionException("Error while generating enum transformer", iox);
                    }
                    finally
                    {
                        IOUtil.close(output);
                    }
                }

                SpringNamespaceHandlerGenerator springNamespaceHandlerGenerator = new SpringNamespaceHandlerGenerator();
                springNamespaceHandlerGenerator.setClasses(classes);

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

                RegistryBootstrapGenerator registryBootstrapGenerator = new RegistryBootstrapGenerator();
                registryBootstrapGenerator.setEnums(enums);

                try
                {
                    output = openRegistryBootstrapFileStream();
                    registryBootstrapGenerator.generate(output);
                }
                catch (IOException iox)
                {
                    throw new MojoExecutionException("Error while generating the registry bootstrap", iox);
                }
                finally
                {
                    IOUtil.close(output);
                }
            }
        }
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


    private OutputStream openRegistryBootstrapFileStream() throws IOException, MojoExecutionException
    {
        File metaInfDirectory = new File(generatedResourcesDirectory(), "META-INF");
        File registryBootstrapDirectory = new File(metaInfDirectory, "services/org/mule/config");
        createDirectory(registryBootstrapDirectory);

        File registryBootstrapFile = new File(registryBootstrapDirectory, "registry-bootstrap.properties");

        return new FileOutputStream(registryBootstrapFile);
    }

}
