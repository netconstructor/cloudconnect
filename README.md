Mule Cloud Connect DevKit
=========================

Mule Cloud Connect is a technology part of Mule ESB 3 release, and allows developers to quickly and efficiently create new connectors to different cloud services while minimizing the amount of code you have to write.

Creating an Empty Connector
---------------------------

Our DevKit is based on Maven, and we offer an archetype that will create the basic project for you. Before we begin you should add our repositories to your *settings.xml* file.

	<mirrors>
	    <mirror>
	      <id>muleforge-release</id>
	      <mirrorOf>muleforge-release</mirrorOf>
	      <name>Muleforge Release Repository</name>
	      <url>http://repository.muleforge.org/release/</url>
	    </mirror>
	    <mirror>
	      <id>muleforge-snapshot</id>
	      <mirrorOf>muleforge-snapshot</mirrorOf>
	      <name>Muleforge Snapshot Repository</name>
	      <url>http://repository.muleforge.org/snapshot/</url>
	    </mirror>
	</mirrors>
	
Now, you can execute our archetype as follows:

	mvn archetype:generate -B -DarchetypeRepository=muleforge-release -DarchetypeGroupId=org.mule.tools -DarchetypeArtifactId=mule-cloud-connector-archetype -DarchetypeVersion=2.0.8-SNAPSHOT -DgroupId=<YOUR GROUP ID> -DartifactId=<YOUR ARTIFACT ID> -Dversion=1.0-SNAPSHOT -Dpackage=<YOUR PACKAGE> -DmuleVersion=3.1 -DcloudService=<CLOUD SERVICE ID> -DcloudServiceType=<CLOUD SERVICE TYPE>
	
As you can see it takes several arguments:

|parameter|description|
|:--------|:----------|
|groupId|The group identifier of the project you are about to create|
|artifactId|The artifact identifier of the project you are about to create|
|package|The package under which the skeleton cloud connector will live|
|muleVersion|The version of Mule ESB you are targeting. Usually 3.1.|
|cloudService|The name of the service your cloud connector will connect to. This is usually things like: PayPal, eBay, Facebook. This string will be used as a base name for your cloud connector class and also for the prefix of the namespace.|
|cloudServiceType|The kind of connectivity that the service employs. You have two choices: HTTP and WSDL. The HTTP is the standard value for this parameter. WSDL will add CXF to your project and it will generate a client from a WSDL file.|