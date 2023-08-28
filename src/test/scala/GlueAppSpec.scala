import org.scalatest.flatspec.AnyFlatSpec

/**
 * A class that runs a local execution of an AWS Glue job within a scalatest
 * Instead of running our local executions, it is preferred to call them from
 * a test framework, where we are able to add assertions for verification.
 */
class GlueAppSpec extends AnyFlatSpec {

  "GlueApp" should "run successfully" in {

    println("Executing test case!")
    // Trigger the execution by directly calling the main class and supplying
    // arguments. AWS Glue job arguments always begin with "--"
    com.fares.AWSGlue.GlueApp.main(Array(
      "--JOB_NAME", "job",
      "--stage", "dev"
    ))

    assert(true)
  }
}
