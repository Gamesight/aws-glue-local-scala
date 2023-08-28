package com.fares.AWSGlue

import com.amazonaws.services.glue.util.JsonOptions
import com.amazonaws.services.glue.{DataSink, DataSource, DynamicFrame, GlueContext}
import org.apache.spark.{SparkConf, SparkContext}
import com.amazonaws.services.glue.util.Job
import com.amazonaws.services.glue.util.GlueArgParser
import org.apache.spark.sql.SparkSession

import scala.collection.JavaConverters._

/**
 * An example that includes all necessary boilerplate code for a deployable   *
 * AWS Glue job script in Scala that can also be tested locally with          *
 * scalatest and sbt.
 */
object GlueApp {

  def main(sysArgs: Array[String]): Unit = {

    // Load the job arguments. "JOB_NAME" is a special argument that gets autofilled
    val args = GlueArgParser.getResolvedOptions(
      sysArgs,
      Seq(
        "JOB_NAME", "stage"
      ).toArray)

    println("Initializing Spark and GlueContext")
    val sparkSession: SparkSession = if (args("stage") == "dev") {
      // For testing, we need to use local execution. You need Java JDK 8 for this to work!
      SparkSession.builder().master("local[*]").getOrCreate()
    } else {
      SparkSession.builder().getOrCreate()
    }
    val sparkContext: SparkContext = sparkSession.sparkContext
    sparkContext.setLogLevel("FATAL") // Can be changed to INFO, ERROR, or WARN
    val glueContext: GlueContext = new GlueContext(sparkContext)


    // Check the custom argument 'stage' to ensure if the execution is local or not
    if (args("stage") == "prod" || args("stage") == "staging") {
      Job.init(if (args("JOB_NAME") != null) args("JOB_NAME") else "test", glueContext, args.asJava)
    }
   
    println("Your code goes here!")

    // Job actions should only happen when executed by AWS Glue, so we ensure correct stage
    if (args("stage") == "prod" || args("stage") == "staging") {
      Job.commit()
    }

  }
}
