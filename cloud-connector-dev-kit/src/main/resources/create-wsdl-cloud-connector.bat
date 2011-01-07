@echo off
setlocal enabledelayedexpansion

::
:: Find the application home.
::
:: %~dp0 is location of current script under NT
set _REALPATH=%~dp0

:: Use the path that was used to launch this script to determine the location
:: of DEVKIT_HOME. Since this script resides in the toplevel folder of the
:: distribution, we need to cut off the last chars (\) from the real
:: path to determine the proper DEVKIT_HOME
set DEVKIT_HOME=%_REALPATH:~0,-1%

:: dynamically evaluate the contents of the classpath
set CP=
for /R %DEVKIT_HOME%\lib %%a in (*.jar) do (
	set CP=!CP!;%%a
)

set ARCHETYPE_SCRIPT_FILE=run-mule-wsdl-cloud-connector-archetype-
for /L %%v in (0,1,1) do set ARCHETYPE_SCRIPT_FILE=!ARCHETYPE_SCRIPT_FILE!!Random!
set ARCHETYPE_SCRIPT_FILE=%TEMP%\%ARCHETYPE_SCRIPT_FILE%.bat

:: ask some questions and generate the scipt file that invokes the archetype
java -cp %CP% org.codehaus.groovy.tools.GroovyStarter --main groovy.ui.GroovyMain %DEVKIT_HOME%\bin\CreateWSDLArchetypeProperties.groovy %ARCHETYPE_SCRIPT_FILE%

:: now run the archetype
call %ARCHETYPE_SCRIPT_FILE%

del /F /Q %ARCHETYPE_SCRIPT_FILE%
