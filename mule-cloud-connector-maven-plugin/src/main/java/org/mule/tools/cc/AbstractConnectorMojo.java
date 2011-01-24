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

import org.mule.tools.cc.model.JavaClass;
import org.mule.tools.cc.parser.ClassParseException;
import org.mule.tools.cc.parser.ClassParser;
import org.mule.tools.cc.parser.qdox.QDoxClassParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.util.IOUtil;

public abstract class AbstractConnectorMojo extends AbstractMojo
{

    /**
     * List of connectors defined for this project
     *
     * @parameter
     * @required
     */
    private List<Connector> connectors;

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
     *
     */
    private ClassParser parser;

    public AbstractConnectorMojo()
    {
        parser = new QDoxClassParser();
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

    public List<Connector> getConnectors()
    {
        return connectors;
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

    protected JavaClass parseCloudConnectorClass(File cloudConnector) throws MojoExecutionException
    {
        if (cloudConnector.exists() == false)
        {
            throw new MojoExecutionException(cloudConnector.getAbsolutePath() + " does not exist");
        }

        InputStream input = null;
        try
        {
            input = new FileInputStream(cloudConnector);
            return parser.parse(input);
        }
        catch (IOException iox)
        {
            throw new MojoExecutionException(
                    "Error while parsing " + cloudConnector.getAbsolutePath(), iox);
        }
        catch (ClassParseException cpe)
        {
            throw new MojoExecutionException(
                    "Error while parsing " + cloudConnector.getAbsolutePath(), cpe);
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
}
