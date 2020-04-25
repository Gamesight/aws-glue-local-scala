package io.gamesight.AWSGlue

import com.amazonaws.services.glue.util.JsonOptions
import com.amazonaws.services.glue.{DynamicFrame, GlueContext, DataSink, DataSource}
import org.apache.spark.{SparkContext, SparkConf}
import com.amazonaws.services.glue.util.Job
import com.amazonaws.services.glue.util.GlueArgParser
import scala.collection.JavaConverters._

object ExampleJob {

  def main(sysArgs: Array[String]): Unit = {
    // Read in the arguments
    val args = GlueArgParser.getResolvedOptions(
      sysArgs,
      Seq(
        "JOB_NAME", "stage", "inputBucket", "outputBucket", "inputPrefix", "outputPrefix"
      ).toArray)

    println("Initializing Spark and GlueContext")

    /**********************************************************************\
     * Here we are initialize the SparkContext. If we are running locally *
     * we need to add a SparkConf that declares a locally spawned Hadoop  *
     * cluster.                                                           *
    \**********************************************************************/
    val sc: SparkContext = if (args("stage") == "dev") {
      // For testing, we need to use local execution
      val conf = new SparkConf().setAppName("GlueExample").setMaster("local")
      new SparkContext(conf)
    } else {
      new SparkContext()
    }

    sc.setLogLevel("FATAL") // this can be changed to INFO, ERROR, or WARN
    val glueContext: GlueContext = new GlueContext(sc)

    /**********************************************************************\
     * Job actions should only happen when executed by AWS Glue, so we    *
     * ensure correct stage. These may need to be updated if you have     *
     * different names for your deployed stages. For this example, if     *
     * either "prod" or "staging" is passed as the "--stage" argument we  *
     * we will execute Job commands. The example test script uses "dev"   *                                                     *
    \**********************************************************************/
    if (args("stage") == "prod" || args("stage") == "staging") {
      Job.init(if (args("JOB_NAME") != null) args("JOB_NAME") else "test", glueContext, args.asJava)
    }

    // Set the connection options using the --inputBucket and --inputPrefix arguments
    val connectionOptions = JsonOptions(Map(
      "paths" ->  Seq(s"s3://${args("inputBucket")}/${args("inputPrefix")}"),
      "compression" -> "gzip",
      "groupFiles" -> "inPartition",
      "groupSize" -> (1024*1024*64).toString()
    ))

    println("Getting Frame")

    // Create the DataSource
    val source: DataSource = glueContext.getSourceWithFormat(
      connectionType = "s3",
      options = connectionOptions,
      transformationContext = "",
      format = "json",
      formatOptions = JsonOptions.empty
    )

    // Convert the source to a DynamicFrame
    val frame: DynamicFrame = source.getDynamicFrame()

    println("Got Frame")

    // Print the schema of our data to the console
    frame.printSchema()

    println("Creating Sink")

    // Create the sink, using the --outputBucket and --outputPrefix arguments
    val sink: DataSink = glueContext.getSinkWithFormat(
      connectionType = "s3",
      options = JsonOptions(Map("path" -> s"s3://${args("outputBucket")}/${args("outputPrefix")}")),
      format = "parquet",
      transformationContext = ""
    )

    println("Writing to Sink")

    // Write the frame to our output destination
    sink.writeDynamicFrame(frame)

    println("Wrote Frame")

    // Job actions should only happen when executed by AWS Glue, so we ensure correct stage
    if (args("stage") == "prod" || args("stage") == "staging") {
      Job.commit()
    }

  }
}
