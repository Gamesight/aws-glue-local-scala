ThisBuild / scalaVersion := "2.12.18"
ThisBuild / organization := "com.fares"

// This is the main config file to manage the sbt project dependencies etc.
lazy val glueetl = (project in file("."))
  .settings(
    name := "aws-glue-local-scala",
    resolvers ++= Seq(
      "aws-glue-etl-artifacts" at "https://aws-glue-etl-artifacts.s3.amazonaws.com/release/"
    ),
    // Here are where dependencies go. They are taken from maven repos
    libraryDependencies ++= Seq(
      "com.amazonaws" % "AWSGlueETL" % "3.0.0", // Glue 3.0
      "org.apache.logging.log4j" % "log4j-core" % "2.13.1",
      "org.apache.spark" %% "spark-core" % "3.1.1" % "provided",
      "org.apache.spark" %% "spark-sql" % "3.1.1" % "provided",
      "software.amazon.awssdk" % "aws-sdk-java" % "2.20.132",
      "org.scalatest" %% "scalatest" % "3.2.10" % "test",
    ),
    dependencyOverrides ++= Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.12.2",
    )
  )
