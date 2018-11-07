@echo off

REM ---------------------------------------------------------------------------
REM    Copyright 2005-2009 WSO2, Inc. http://www.wso2.org
REM
REM  Licensed under the Apache License, Version 2.0 (the "License");
REM  you may not use this file except in compliance with the License.
REM  You may obtain a copy of the License at
REM
REM      http://www.apache.org/licenses/LICENSE-2.0
REM
REM  Unless required by applicable law or agreed to in writing, software
REM  distributed under the License is distributed on an "AS IS" BASIS,
REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
REM  See the License for the specific language governing permissions and
REM  limitations under the License.

rem ---------------------------------------------------------------------------
rem Main Script for WSO2 Carbon
rem
rem Environment Variable Prequisites
rem
rem   CARBON_HOME   Home of CARBON installation. If not set I will  try
rem                   to figure it out.
rem
rem   JAVA_HOME       Must point at your Java Development Kit installation.
rem
rem   JAVA_OPTS       (Optional) Java runtime options used when the commands
rem                   is executed.
rem ---------------------------------------------------------------------------

rem ----- if JAVA_HOME is not set we're not happy ------------------------------
:checkJava

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
goto checkServer

:noJavaHome
echo "You must set the JAVA_HOME variable before running CARBON."
goto end

rem ----- Only set CARBON_HOME if not already set ----------------------------
:checkServer
rem %~sdp0 is expanded pathname of the current script under NT with spaces in the path removed
if "%CARBON_HOME%"=="" set CARBON_HOME=%~sdp0..
SET curDrive=%cd:~0,1%
SET wsasDrive=%CARBON_HOME:~0,1%
if not "%curDrive%" == "%wsasDrive%" %wsasDrive%:

rem find CARBON_HOME if it does not exist due to either an invalid value passed
rem by the user or the %0 problem on Windows 9x
if not exist "%CARBON_HOME%\bin\version.txt" goto noServerHome

set AXIS2_HOME=%CARBON_HOME%
goto updateClasspath

:noServerHome
echo CARBON_HOME is set incorrectly or CARBON could not be located. Please set CARBON_HOME.
goto end

rem ----- update classpath -----------------------------------------------------
:updateClasspath

setlocal EnableDelayedExpansion
cd %CARBON_HOME%
set CARBON_CLASSPATH=
FOR %%C in ("%CARBON_HOME%\lib\*.jar") DO set CARBON_CLASSPATH=!CARBON_CLASSPATH!;".\lib\%%~nC%%~xC"

set CARBON_CLASSPATH="%JAVA_HOME%\lib\tools.jar";%CARBON_CLASSPATH%;

rem ----- Process the input command -------------------------------------------

rem Slurp the command line arguments. This loop allows for an unlimited number
rem of arguments (up to the command line limit, anyway).


:setupArgs
if ""%1""=="""" goto doneStart

if ""%1""==""-run""     goto commandLifecycle
if ""%1""==""--run""    goto commandLifecycle
if ""%1""==""run""      goto commandLifecycle

if ""%1""==""-restart""  goto commandLifecycle
if ""%1""==""--restart"" goto commandLifecycle
if ""%1""==""restart""   goto commandLifecycle

if ""%1""==""debug""    goto commandDebug
if ""%1""==""-debug""   goto commandDebug
if ""%1""==""--debug""  goto commandDebug

if ""%1""==""help""     goto getHelp
if ""%1""==""-help""    goto getHelp
if ""%1""==""--help""   goto getHelp
if ""%1""==""?""        goto getHelp
if ""%1""==""-?""       goto getHelp

shift
goto setupArgs

rem ----- commandVersion -------------------------------------------------------
:commandVersion
shift
echo ${product.name} v${product.version}
goto end

rem ----- commandDebug ---------------------------------------------------------
:commandDebug
shift
set DEBUG_PORT=%1
if "%DEBUG_PORT%"=="" goto noDebugPort
set JAVA_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=%DEBUG_PORT%
goto findJdk

:noDebugPort
echo Please specify the debug port after the --debug option
goto end

rem ----- commandLifecycle -----------------------------------------------------
:commandLifecycle
goto findJdk

rem ----- getHelp --------------------------------------------------------------
:getHelp
  echo Usage: wso2server.bat [command]
  echo command:
  echo   --start        Start Carbon as a Windows Service
  echo   --stop         Stop the Carbon Windows Service
  echo   --run          Run CARBON
  echo   --debug        Start CARBON in remote debugging mode. You need to specify the debugging port after this argument.
  echo   --version      What version of CARBON are you running?
  echo   --help         This help message
goto end

rem ----- doneStart ------------------------------------------------------------
rem This label provides a place for the argument list loop to break out
rem and for NT handling to skip to.

:doneStart
if "%OS%"=="Windows_NT" @setlocal
if "%OS%"=="WINNT" @setlocal

rem ---------- Handle the SSL Issue with proper JDK version --------------------
rem find the version of the jdk
:findJdk

set CMD=RUN %*

:checkJdk15
"%JAVA_HOME%\bin\java" -version 2>&1 | findstr "1.5" >NUL
IF ERRORLEVEL 1 goto checkJdk16
goto jdk15

:checkJdk16
"%JAVA_HOME%\bin\java" -version 2>&1 | findstr "1.6" >NUL
IF ERRORLEVEL 1 goto unknownJdk
goto jdk16

:unknownJdk
echo Starting WSO2 Carbon (in unsupported JDK)
echo [ERROR] CARBON is supported only on JDK 1.5 and higher
goto end

:jdk15
  set CARBON_CLASSPATH="%CARBON_HOME%\lib\bcprov-jdk15-132.jar";%CARBON_CLASSPATH%
	goto runServer
:jdk16
  set CARBON_CLASSPATH="%CARBON_HOME%\lib\bcprov-jdk15-132.jar";%CARBON_CLASSPATH%
	goto runServer

rem ----------------- Execute The Requested Command ----------------------------

:runServer
cd %CARBON_HOME%

rem ---------- Add jar files inside extensions folder to classpath ----------------
FOR %%C in ("%CARBON_HOME%\lib\extensions\*.jar") DO set CARBON_PATCH_CLASSPATH=!CARBON_PATCH_CLASSPATH!;".\lib\extensions\%%~nC%%~xC"

set CARBON_CLASSPATH=.\lib\extensions;%CARBON_PATCH_CLASSPATH%;.\lib;%CARBON_CLASSPATH%


set JAVA_ENDORSED=".\lib\endorsed";"%JAVA_HOME%\jre\lib\endorsed";"%JAVA_HOME%\lib\endorsed"

"%JAVA_HOME%\bin\java" -Xms256m -Xmx512m -XX:MaxPermSize=128m -Dcom.sun.management.jmxremote -classpath %CARBON_CLASSPATH% %JAVA_OPTS% -Djava.endorsed.dirs=%JAVA_ENDORSED% -Dcarbon.registry.root=/ -Dcarbon.home="%CARBON_HOME%" -Dwso2.server.standalone=true -Djava.io.tmpdir="%CARBON_HOME%\tmp" -Dwso2.carbon.xml=%CARBON_HOME%\conf\carbon.xml -Dwso2.transports.xml=" " org.wso2.carbon.server.RepositoryMetadataWriter %CMD%

:end
goto endlocal

:endlocal