#!/bin/sh
# ----------------------------------------------------------------------------
#  Copyright 2005-2009 WSO2, Inc. http://www.wso2.org
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

# ----------------------------------------------------------------------------
# Main Script for the WSO2 Carbon Server
#
# Environment Variable Prequisites
#
#   CARBON_HOME   Home of WSO2 Carbon installation. If not set I will  try
#                   to figure it out.
#
#   JAVA_HOME       Must point at your Java Development Kit installation.
#
#   JAVA_OPTS       (Optional) Java runtime options used when the commands
#                   is executed.
#
# NOTE: Borrowed generously from Apache Tomcat startup scripts.
# -----------------------------------------------------------------------------

DARWIN_TOOLS=""
# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
os400=false;
mingw=false;
case "`uname`" in
CYGWIN*) cygwin=true;;
MINGW*) mingw=true;;
OS400*) os400=true;;
Darwin*) darwin=true
        if [ -z "$JAVA_VERSION" ] ; then
             JAVA_VERSION="CurrentJDK"
           else
             echo "Using Java version: $JAVA_VERSION"
           fi
           if [ -z "$JAVA_HOME" ] ; then
             JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Home
             DARWIN_TOOLS=$JAVA_HOME/Classes/Classes.jar
           fi
           ;;
esac

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`

# Only set CARBON_HOME if not already set
[ -z "$CARBON_HOME" ] && CARBON_HOME=`cd "$PRGDIR/.." ; pwd`

# Set AXIS2_HOME. Needed for One Click JAR Download
AXIS2_HOME=$CARBON_HOME

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$CARBON_HOME" ] && CARBON_HOME=`cygpath --unix "$CARBON_HOME"`
  [ -n "$AXIS2_HOME" ] && CARBON_HOME=`cygpath --unix "$CARBON_HOME"`
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# For OS400
if $os400; then
  # Set job priority to standard for interactive (interactive - 6) by using
  # the interactive priority - 6, the helper threads that respond to requests
  # will be running at the same priority as interactive jobs.
  COMMAND='chgjob job('$JOBNAME') runpty(6)'
  system $COMMAND

  # Enable multi threading
  QIBM_MULTI_THREADED=Y
  export QIBM_MULTI_THREADED
fi

# For Migwn, ensure paths are in UNIX format before anything is touched
if $mingw ; then
  [ -n "$CARBON_HOME" ] &&
    CARBON_HOME="`(cd "$CARBON_HOME"; pwd)`"
  [ -n "$JAVA_HOME" ] &&
    JAVA_HOME="`(cd "$JAVA_HOME"; pwd)`"
  [ -n "$AXIS2_HOME" ] &&
    CARBON_HOME="`(cd "$CARBON_HOME"; pwd)`"
  # TODO classpath?
fi

if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=java
  fi
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly."
  echo " CARBON cannot execute $JAVACMD"
  exit 1
fi

# if JAVA_HOME is not set we're not happy
if [ -z "$JAVA_HOME" ]; then
  echo "You must set the JAVA_HOME variable before running CARBON."
  exit 1
fi


# update classpath
CARBON_CLASSPATH=""
for f in "$CARBON_HOME"/lib/*.jar
do
  CARBON_CLASSPATH="$CARBON_CLASSPATH":$f
done
CARBON_CLASSPATH="$JAVA_HOME/lib/tools.jar":"$CARBON_CLASSPATH":"$CLASSPATH":"$DARWIN_TOOLS"

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  JAVA_HOME=`cygpath --absolute --windows "$JAVA_HOME"`
  CARBON_HOME=`cygpath --absolute --windows "$CARBON_HOME"`
  AXIS2_HOME=`cygpath --absolute --windows "$CARBON_HOME"`
  CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
  JAVA_ENDORSED_DIRS=`cygpath --path --windows "$JAVA_ENDORSED_DIRS"`
fi

# ----- Process the input command ----------------------------------------------
for c in $*
do
    if [ "$c" = "--debug" ] || [ "$c" = "-debug" ] || [ "$c" = "debug" ]; then
          CMD="--debug"
          continue
    elif [ "$CMD" = "--debug" ]; then
          if [ -z "$PORT" ]; then
                PORT=$c
          fi      
    elif [ "$c" = "--stop" ] || [ "$c" = "-stop" ] || [ "$c" = "stop" ]; then
          CMD="stop"
    elif [ "$c" = "--start" ] || [ "$c" = "-start" ] || [ "$c" = "start" ]; then
          CMD="start"
    elif [ "$c" = "--console" ] || [ "$c" = "-console" ] || [ "$c" = "console" ]; then
          CMD="console"
    elif [ "$c" = "--version" ] || [ "$c" = "-version" ] || [ "$c" = "version" ]; then
          CMD="version"
    elif [ "$c" = "--restart" ] || [ "$c" = "-restart" ] || [ "$c" = "restart" ]; then
          CMD="restart"
    elif [ "$c" = "--dump" ] || [ "$c" = "-dump" ] || [ "$c" = "dump" ]; then
          CMD="dump"
    elif [ "$c" = "--status" ] || [ "$c" = "-status" ] || [ "$c" = "status" ]; then
          CMD="status"
    fi
done

if [ "$CMD" = "--debug" ]; then
  if [ "$PORT" = "" ]; then
    echo " Please specify the debug port after the --debug option"
    exit 1
  fi
  CMD="RUN"
  JAVA_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=$PORT"
  echo "Please start the remote debugging client to continue..."
elif [ "$CMD" = "start" ]; then
  $CARBON_HOME/bin/daemon.sh start
  exit 0
elif [ "$CMD" = "stop" ]; then
  $CARBON_HOME/bin/daemon.sh stop
  exit 0
elif [ "$CMD" = "console" ]; then
  $CARBON_HOME/bin/daemon.sh console
  exit 0
elif [ "$CMD" = "restart" ]; then
  $CARBON_HOME/bin/daemon.sh restart
  exit 0
elif [ "$CMD" = "dump" ]; then
  $CARBON_HOME/bin/daemon.sh dump
  exit 0
elif [ "$CMD" = "status" ]; then
  $CARBON_HOME/bin/daemon.sh status
  exit 0
elif [ "$CMD" = "version" ]; then
  cat $CARBON_HOME/bin/version.txt
  cat $CARBON_HOME/bin/wso2carbon-version.txt
  exit 0
fi

# ---------- Handle the SSL Issue with proper JDK version --------------------
jdk_15=`$JAVA_HOME/bin/java -version 2>&1 | grep 1.5`
jdk_16=`$JAVA_HOME/bin/java -version 2>&1 | grep 1.6`

if [ "$jdk_15" ]; then
   CARBON_CLASSPATH="$CARBON_HOME/lib/bcprov-jdk15-@bcprov_jdk15_version@.jar":$CARBON_CLASSPATH
elif [ "$jdk_16" ]; then
   CARBON_CLASSPATH="$CARBON_HOME/lib/bcprov-jdk15-@bcprov_jdk15_version@.jar":$CARBON_CLASSPATH
else
   echo " [ERROR] CARBON is supported only on JDK 1.5 and higher"
   exit 1
fi

#---------- Add jars to classpath ------------------

for f in "$CARBON_HOME"/lib/patches/*.jar
do
  CARBON_PATCH_CLASSPATH="$CARBON_PATCH_CLASSPATH":$f
done

CARBON_XBOOTCLASSPATH=""
for f in "$CARBON_HOME"/lib/xboot/*.jar
do
  CARBON_XBOOTCLASSPATH="$CARBON_XBOOTCLASSPATH":$f
done

CARBON_CLASSPATH="$CARBON_HOME/lib/patches":"$CARBON_PATCH_CLASSPATH":"$CARBON_HOME/lib":"$CARBON_CLASSPATH"


# ----- Execute The Requested Command -----------------------------------------

cd "$CARBON_HOME"

exec "$JAVACMD" \
-Xbootclasspath/a:"$CARBON_XBOOTCLASSPATH" \
-Xms256m -Xmx512m -XX:MaxPermSize=128m \
$JAVA_OPTS \
-Dimpl.prefix=Carbon \
-Dcom.sun.management.jmxremote \
-classpath "$CARBON_CLASSPATH" \
-Djava.endorsed.dirs="$CARBON_HOME/lib/endorsed":"$JAVA_HOME/jre/lib/endorsed":"$JAVA_HOME/lib/endorsed" \
-Djava.io.tmpdir="$CARBON_HOME/tmp" \
-Dwso2.server.standalone=true \
-Dcarbon.registry.root=/ \
-Dcarbon.home="$CARBON_HOME" -Dwso2.carbon.xml="$CARBON_HOME/conf/carbon.xml" \
-Dwso2.transports.xml="$CARBON_HOME/conf/transports.xml" \
-Djava.util.logging.config.file="$CARBON_HOME/lib/log4j.properties" \
org.wso2.carbon.server.Main $*
