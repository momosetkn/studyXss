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
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

javaOptions ++= Seq(
"-Xdebug",
"-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
),
