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

package org.mule.tools.cloudconnect.generator;

import java.util.HashMap;
import java.util.Map;

public class ReadmeGenerator extends AbstractTemplateGenerator
{
    private String name;
    private String description;
    private String groupId;
    private String artifactId;
    private String version;
    private String repoId;
    private String repoUrl;
    private String repoName;
    private String repoLayout;
    private static final String README_TEMPLATE = "readme.ftl";

    @Override
    protected Map<String, Object> createModel()
    {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("name", name);
        root.put("description", description);
        root.put("groupId", groupId);
        root.put("artifactId", artifactId);
        root.put("version", version);
        root.put("repoId", repoId);
        root.put("repoUrl", repoUrl);
        root.put("repoName", repoName);
        root.put("repoLayout", repoLayout);
        root.put("class", getJavaClass());

        return root;
    }

    @Override
    protected String getTemplate()
    {
        return README_TEMPLATE;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public void setArtifactId(String artifactId)
    {
        this.artifactId = artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getRepoId()
    {
        return repoId;
    }

    public void setRepoId(String repoId)
    {
        this.repoId = repoId;
    }

    public String getRepoUrl()
    {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl)
    {
        this.repoUrl = repoUrl;
    }

    public String getRepoName()
    {
        return repoName;
    }

    public void setRepoName(String repoName)
    {
        this.repoName = repoName;
    }

    public String getRepoLayout()
    {
        return repoLayout;
    }

    public void setRepoLayout(String repoLayout)
    {
        this.repoLayout = repoLayout;
    }
}
