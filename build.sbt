name := "udemy-akka-streams"

version := "0.1"

scalaVersion := "2.12.10"

lazy val akkaVersion = "2.5.20"
lazy val scalaTestVersion = "3.0.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion
)
