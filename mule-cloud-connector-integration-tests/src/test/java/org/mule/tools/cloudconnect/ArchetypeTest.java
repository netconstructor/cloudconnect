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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.junit.Before;
import org.junit.Test;

public class ArchetypeTest
{

    private static final File ROOT = new File("target/test-classes/");
    private static final String ARCHETYPE_PROPERTIES = "archetype.properties";

    private Properties archetypeProperties;

    @Before
    public void setUp() throws VerificationException, IOException
    {
        InputStream stream = ClassLoader.getSystemResourceAsStream(ARCHETYPE_PROPERTIES);
        archetypeProperties = new Properties(System.getProperties());
        archetypeProperties.load(stream);

        Verifier verifier = new Verifier(ROOT.getAbsolutePath());

        // deleting a former created artefact from the archetype to be tested
        verifier.deleteArtifact(getArchetypeGroupId(), getArchetypeArtifactId(), getArchetypeVersion(), null);

        // delete the created maven project
        verifier.deleteDirectory(getArchetypeArtifactId());
    }

    private String getArchetypeVersion()
    {
        return archetypeProperties.getProperty("archetypeVersion");
    }

    private String getArchetypeArtifactId()
    {
        return archetypeProperties.getProperty("archetypeArtifactId");
    }

    private String getArchetypeGroupId()
    {
        return archetypeProperties.getProperty("archetypeGroupId");
    }

    private String getArtifactId()
    {
        return archetypeProperties.getProperty("artifactId");
    }

    @Test
    public void testGenerateArchetype() throws VerificationException
    {
        Verifier verifier = new Verifier(ROOT.getAbsolutePath());
        verifier.setSystemProperties(archetypeProperties);
        verifier.setAutoclean(false);

        verifier.executeGoal("archetype:generate");

        verifier.verifyErrorFreeLog();

        verifier = new Verifier(ROOT.getAbsolutePath() + "/" + getArtifactId());
        verifier.setAutoclean(true);
        verifier.executeGoal("test");

        verifier.verifyErrorFreeLog();

        verifier.verifyTextInLog("namespace-handler-generate"); // jasmine tests should be run
        verifier.verifyTextInLog("schema-generate"); // compression should be run
    }
}
