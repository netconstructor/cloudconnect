@echo off
setlocal enabledelayedexpansion

::
:: Find the application home.
::
:: %~dp0 is location of current script under NT
set _REALPATH=%~dp0

:: Use the path that was used to launch this script to determine the location
:: of MULE_HOME. Since this script resides in the bin folder of the Mule 
:: distribution, we need to cut off the last 5 chars (\bin\) from the real
:: path to determine the proper MULE_HOME
set MULE_HOME=%_REALPATH:~0,-5%

:: dynamically evaluate the name of the groovy jar
for /F %%v in ('dir /b %MULE_HOME%\lib\opt^| findstr groovy') do set GROOVY_JAR=%%v
set GROOVY_JAR=%MULE_HOME%\lib\opt\%GROOVY_JAR%

set ARCHETYPE_SCRIPT_FILE=run-mule-wsdl-cloud-connector-archetype-
for /L %%v in (0,1,1) do set ARCHETYPE_SCRIPT_FILE=!ARCHETYPE_SCRIPT_FILE!!Random!
set ARCHETYPE_SCRIPT_FILE=%TEMP%\%ARCHETYPE_SCRIPT_FILE%.bat

:: ask some questions and generate the scipt file that invokes the archetype
java -cp %GROOVY_JAR% org.codehaus.groovy.tools.GroovyStarter --main groovy.ui.GroovyMain %MULE_HOME%/bin/CreateWSDLArchetypeProperties.groovy %ARCHETYPE_SCRIPT_FILE%

:: now run the archetype
call %ARCHETYPE_SCRIPT_FILE%

del /F /Q %ARCHETYPE_SCRIPT_FILE%
