#!/usr/bin/env sh

DIR="$(cd "$(dirname "$0")" && pwd)"

CLASSPATH="$DIR/gradle/wrapper/gradle-wrapper.jar"

if [ -n "$JAVA_HOME" ]; then
  JAVACMD="$JAVA_HOME/bin/java"
else
  JAVACMD="java"
fi

JAVA_OPTS=${JAVA_OPTS:-}
GRADLE_OPTS=${GRADLE_OPTS:-}
DEFAULT_JVM_OPTS="-Xmx64m -Xms64m"

exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
