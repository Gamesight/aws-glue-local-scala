ThisBuild / scalaVersion := "2.11.1"
ThisBuild / organization := "io.gamesight"

lazy val glueetl = (project in file("."))
  .settings(
    name := "aws-glue-local-scala",
    resolvers ++= Seq(
      "aws-glue-etl-artifacts" at "https://aws-glue-etl-artifacts.s3.amazonaws.com/release/"
    ),
    libraryDependencies ++= Seq(
      "com.amazonaws" % "AWSGlueETL" % "1.0.0",
      "org.apache.logging.log4j" % "log4j-core" % "2.13.1",
      "org.apache.spark" %% "spark-core" % "2.4.3" % "provided",
      "org.apache.spark" %% "spark-mllib" % "2.4.3" % "provided",
      "org.apache.spark" %% "spark-sql" % "2.4.3" % "provided",
      "org.scalactic" %% "scalactic" % "3.1.1",
      "org.scalamock" %% "scalamock" % "4.4.0" % "test",
      "org.scalatest" %% "scalatest" % "3.1.1" % "test",
      "software.amazon.awssdk" % "aws-sdk-java" % "2.13.0",
    ),
    dependencyOverrides ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-core" % "2.6.7",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.7.1",
    )
  )
