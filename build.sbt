val ScalatraVersion = "2.6.3"

organization := "momosetkn"

name := "try_scalatra"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.6"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.9.v20180320" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  // https://mvnrepository.com/artifact/io.pebbletemplates/pebble
  "io.pebbletemplates" % "pebble" % "3.0.2",
  // https://mvnrepository.com/artifact/com.vladsch.flexmark/flexmark-all
  "com.vladsch.flexmark" % "flexmark-all" % "0.34.16",
  // https://mvnrepository.com/artifact/commons-io/commons-io
  "commons-io" % "commons-io" % "2.6",
  // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
  "org.apache.commons" % "commons-lang3" % "3.7"

)

enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)

javaOptions ++= Seq(
"-Xdebug",
"-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
)
