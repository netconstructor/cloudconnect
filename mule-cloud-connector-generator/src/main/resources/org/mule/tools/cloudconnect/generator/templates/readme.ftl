${name}
<@titleFill>${name}</@titleFill>

${description}

Installation
------------

The connector can either be installed for all applications running within the Mule instance or can be setup to be used
for a single application.

*All Applications*

Download the connector from the link above and place the resulting jar file in
/lib/user directory of the Mule installation folder.

*Single Application*

To make the connector available only to single application then place it in the
lib directory of the application otherwise if using Maven to compile and deploy
your application the following can be done:

Add the connector's maven repo to your pom.xml:

    <repositories>
        <repository>
            <id>${repoId}</id>
            <name>${repoName}</name>
            <url>${repoUrl}</url>
            <layout>${repoLayout}</layout>
        </repsitory>
    </repositories>

Add the connector as a dependency to your project. This can be done by adding
the following under the <dependencies> element in the pom.xml file of the
application:

    <dependency>
        <groupId>${groupId}</groupId>
        <artifactId>${artifactId}</artifactId>
        <version>${version}</version>
    </dependency>

Configuration
-------------

You can configure the connector as follows:

    <${class.getNamespacePrefix()}:config<#if class.getFactory()?has_content><#list class.getFactory().getProperties() as property><#if property.isConfigurable()> <@uncapitalize>${property.getName()}</@uncapitalize>="<#if property.getExample()?has_content>${property.getExample()}<#else>value</#if>"</#if></#list><#else><#list class.getProperties() as property><#if property.isConfigurable()> <@uncapitalize>${property.getName()}</@uncapitalize>="<#if property.getExample()?has_content>${property.getExample()}<#else>value</#if>"</#if></#list></#if>/>

Here is detailed list of all the configuration attributes:

| attribute | description | optional | default value |
|:-----------|:-----------|:---------|:--------------|
|name|Give a name to this configuration so it can be later referenced by config-ref.|yes||
<#if class.getFactory()?has_content>
<#list class.getFactory().getProperties() as property>
|<@uncapitalize>${property.getName()}</@uncapitalize>|<#if property.getDescription()?has_content>${property.getDescription()}</#if>|<#if property.isOptional()>yes<#else>no</#if>|${property.getDefaultValue()}
</#list>
<#else>
<#list class.getProperties() as property>
<#if property.isConfigurable()>
|<@uncapitalize>${property.getName()}</@uncapitalize>|<#if property.getDescription()?has_content>${property.getDescription()}</#if>|<#if property.isOptional()>yes<#else>no</#if>|${property.getDefaultValue()}
</#if>
</#list>
</#if>


<#list class.getMethods() as method>
<#if method.isOperation()>
<@titleCamelCase>${method.getElementName()}</@titleCamelCase>
<@subTitleFill><@titleCamelCase>${method.getElementName()}</@titleCamelCase></@subTitleFill>
<#if method.getDescription()?has_content>

${method.getDescription()}
</#if>
<#if method.getExample()?has_content>

<@indent>${method.getExample()}</@indent>
</#if>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
<#list method.getParameters() as parameter>
|<@uncapitalize>${parameter.getElementName()}</@uncapitalize>|<#if parameter.getDescription()?has_content>${parameter.getDescription()}</#if>|<#if parameter.isOptional()>yes<#else>no</#if>|${parameter.getDefaultValue()}|<#if parameter.getType().isEnum()><#list parameter.getType().getValues() as value>*${value}*<#if value_has_next>, </#if></#list></#if>
</#list>
</#if>

</#list>