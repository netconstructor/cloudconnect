
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
    question("Name of the cloud service the new connector uses", "cloudService")
    generateArtifactIdAndJavaPackage()

    question("Maven group id of the new connector", "groupId", false)
    question("Maven artifact id of the new connector\n(should use naming convention mule-module-<yourname>)",
        "artifactId", false)
    question("Maven version of the new connector", "version", false)
    question("Java package for the new connector\n(should use the naming convention org.mule.module.<yourname>)",
        "package", false)
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

def generateArtifactIdAndJavaPackage()
{
    def cloudService = archetypeProperties.cloudService

    archetypeProperties.setProperty("artifactId", "mule-module-${cloudService.toLowerCase()}")
    archetypeProperties.setProperty("package", "org.mule.module.${cloudService.toLowerCase()}")
}

def generateArchetypeScriptFile(String filename)
{
    def mavenExecutable = "mvn"
    if (System.getProperty("os.name").startsWith("Windows"))
    {
        mavenExecutable = "mvn.bat"
    }

    def muleVersion = "3.1.0"
    def archetypeVersion = "1.0-SNAPSHOT"

    def outputFile = new File(filename)
    outputFile.withWriter
    {
        writer ->

        writer.write("${mavenExecutable} archetype:generate -B")
        writer.write(" -DarchetypeGroupId=org.mule.tools")
        writer.write(" -DarchetypeArtifactId=mule-wsdl-cloud-connector-archetype")
        writer.write(" -DarchetypeVersion=${archetypeVersion}")
        writer.write(" -DgroupId=${archetypeProperties.groupId}")
        writer.write(" -DartifactId=${archetypeProperties.artifactId}")
        writer.write(" -Dversion=${archetypeProperties.version}")
        writer.write(" -Dpackage=${archetypeProperties.package}")
        writer.write(" -DmuleVersion=${muleVersion}")
        writer.write(" -DcloudService=${archetypeProperties.cloudService}")
        writer.write(" -DcloudServiceLower=${archetypeProperties.cloudService.toLowerCase()}")
        writer.write(" -Dwsdl=${archetypeProperties.wsdl}")

        // to run a snapshot version of the archetype we must declare repository to find it
        if (archetypeVersion.endsWith("SNAPSHOT"))
        {
            writer.write(" -DarchetypeRepository=http://snapshots.repository.codehaus.org")
        }

        writer.newLine()
    }
}
