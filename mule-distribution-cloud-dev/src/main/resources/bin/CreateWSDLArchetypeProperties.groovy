
stdinReader = new BufferedReader(new InputStreamReader(System.in))
outputFilename = args[0]

archetypeProperties = createDefaultProperties()
askQuestions()
generateArchetypeScriptFile(outputFilename)

def createDefaultProperties()
{
    Properties defaultProperties = new Properties()
    defaultProperties.setProperty("groupId", "org.mule.modules")
    defaultProperties.setProperty("version", "1.0-SNAPSHOT")
    return defaultProperties
}

def askQuestions()
{
    question("Maven group id of the new connector", "groupId", false)
    question("Maven artifact id of the new connector\n(should use naming convention mule-module-<yourname>)",
        "artifactId")
    question("Maven version of the new connector", "version", false)
    question("Java package for the new connector\n(should use the naming convention org.mule.module.<yourname>)",
        "package")
    question("Name of the cloud service the new connector hosts", "cloudService")
    question("Location of the WSDL (can be a local file or a URL)", "wsdl")
}

def question(String text, String key, boolean required=true)
{
    printQuestion(text, key)

    def line = stdinReader.readLine()
    if (required && !line)
    {
        // invalid answer, try again
        question(text, key, required)
    }

    if (line)
    {
        archetypeProperties.setProperty(key, line)
    }
}

def printQuestion(String text, String key)
{
    println()
    println(text)

    def defaultValue = archetypeProperties.getProperty(key)
    if (defaultValue == null)
    {
        defaultValue = ""
    }
    println("[default: ${defaultValue}]")
    print("> ")
}

def generateArchetypeScriptFile(String filename)
{
    def outputFile = new File(filename)
    outputFile.withWriter
    {
        writer ->

        writer.write("""\
mvn archetype:generate -B -DarchetypeGroupId=org.mule.tools \\
    -DarchetypeArtifactId=mule-wsdl-cloud-connector-archetype \\
    -DarchetypeVersion=1.0-SNAPSHOT \\
    -DgroupId=${archetypeProperties.groupId} \\
    -DartifactId=${archetypeProperties.artifactId} \\
    -Dversion=${archetypeProperties.version} \\
    -Dpackage=${archetypeProperties.package} \\
    -DcloudService=${archetypeProperties.cloudService} \\
    -DcloudServiceLower=${archetypeProperties.cloudService.toLowerCase()} \\
    -Dwsdl=${archetypeProperties.wsdl}
""")
    }
}
