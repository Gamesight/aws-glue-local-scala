/******************************************************************************\
 * A  class that runs a local execution of an AWS Glue job within a scalatest *
 * Instead of running our local executions, it is preferred to call them from *
 * a test framework, where we are able to add assertions for verification.    *
 *                                                                            *
 * Org: Gamesight - https://gamesight.io                                      *
 * Author: jeremy@gamesight.io                                                *
 * License: MIT                                                               *
 * Copyright (c) 2020 Gamesight                                               *
\******************************************************************************/

import org.scalatest._

class ExampleSpec extends FunSpec {
  describe("Example") {
    it("should run the job") {

      println(s"Starting ExampleJob at ${new java.util.Date()}")

      // Trigger the execution by directly calling the main class and supplying
      // arguments. AWS Glue job arguments always begin with "--" so that the
      // resolver can correctly convert it to a Map
      io.gamesight.AWSGlue.ExampleJob.main(Array(
        "--JOB_NAME", "job",
        "--stage", "dev",
        "--inputBucket", "<YOUR BUCKET NAME>",
        "--outputBucket", "<YOUR OUTPUT BUCKET NAME>",
        "--inputPrefix", "<YOUR INPUT PREFIX>",
        "--outputPrefix", "<YOUR OUTPUT PREFIX>"
      ))

      println(s"ExampleJob Finished at ${new java.util.Date()}")

    }
  }
}
