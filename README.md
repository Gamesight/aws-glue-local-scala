# AWS GLUE LOCAL SCALA

This is a tool to develop and test AWS Glue scripts written in Scala. It uses SBT to manage the necessary resources for local testing. It was inspired by the documentation on locally testing Glue here: https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-etl-libraries.html which suggests local installations of packages and running with the Maven. At Gamesight.io, we wanted something that we could easily integrate into our CI/CD systems. This tool enables us to do that.

### Dependencies
* Java 8 - Later versions of Java will not work with AWS Glue
* SBT Version 1.3.10 - get it here https://www.scala-sbt.org/index.html
* Scala 2.11.1 or later

### Setup

1. Clone the repository.
2. Update the test.  
Update the following portion of ExampleSpec.scala with real S3 bucket names and prefixes that you are have privileges to use (List, Read, Write).
```
io.gamesight.AWSGlue.ExampleJob.main(Array(
  "--JOB_NAME", "job",
  "--stage", "dev",
  "--inputBucket", "<YOUR BUCKET NAME>",
  "--outputBucket", "<YOUR OUTPUT BUCKET NAME>",
  "--inputPrefix", "<YOUR INPUT PREFIX>",
  "--outputPrefix", "<YOUR OUTPUT PREFIX>"
))
```
3. Compile the package.  
`sbt clean compile`
4. Verify that your AWS credentials are active
5. Run the test example.  
`sbt test`

### Usage

We suggest that you start with ExampleJob.scala as the boilerplate for development. There is some additional handling for stage separation that allows the script to run locally. The calls to `Job` functions should only run during executions within the AWS Glue environment. Additionally, when `dev` is passed to the `--stage` argument, the example adds local configuration to the `SparkContext` without affecting deployed executions. The example can be executed either locally or deployed, as long as the correct arguments are passed.  

While it is possible to run ExampleJob or any user-defined job from the command line with `sbt "run <args here>"`, we also that executions are triggered from within a testing framework. The example uses http://www.scalatest.org/ which allows easy control over the main class arguments and gives the ability to add assertions. This also fits with our goal of being able to integrate AWS Glue job scripts into a CI/CD flow.  

NOTE: Testing locally should be done with a small data set. Local executions do not have the parallelism benefits of a true distributed cluster. Instead, the local testing works on a single-node cluster.

### Deployment

In order to deploy your glue job, either copy and paste the contents of your tested script using the AWS console, or upload the script to S3 using your preferred deployment tool.

### How to Collaborate

If you have questions or ideas, feel free to post issues or email jeremy@gamesight.io
