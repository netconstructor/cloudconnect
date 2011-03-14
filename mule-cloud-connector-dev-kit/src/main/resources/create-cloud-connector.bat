@REM
@REM Mule Cloud Connector Development Kit
@REM Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM     http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM

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

set ARCHETYPE_SCRIPT_FILE=run-mule-cloud-connector-archetype-
for /L %%v in (0,1,1) do set ARCHETYPE_SCRIPT_FILE=!ARCHETYPE_SCRIPT_FILE!!Random!
set ARCHETYPE_SCRIPT_FILE=%TEMP%\%ARCHETYPE_SCRIPT_FILE%.bat

:: ask some questions and generate the scipt file that invokes the archetype
java -cp %CP% org.codehaus.groovy.tools.GroovyStarter --main groovy.ui.GroovyMain %DEVKIT_HOME%\bin\CreateArchetypeProperties.groovy %ARCHETYPE_SCRIPT_FILE%

:: now run the archetype
call %ARCHETYPE_SCRIPT_FILE%

del /F /Q %ARCHETYPE_SCRIPT_FILE%
