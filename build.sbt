organization := "com.alliander"
name := "ask-brain"

version := "0.1"
scalaVersion := "2.13.0"

lazy val akkaHttpVer = "10.1.9"
lazy val akkaVer = "2.5.25"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"            % akkaHttpVer,
  "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVer,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVer,
  "com.typesafe.akka" %% "akka-stream"          % akkaVer,

  "com.fasterxml.uuid" % "java-uuid-generator"  % "3.1.5",

  "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVer % Test,
  "com.typesafe.akka" %% "akka-testkit"         % akkaVer     % Test,
  "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVer     % Test,
  "org.scalatest"     %% "scalatest"            % "3.0.8"     % Test
)
