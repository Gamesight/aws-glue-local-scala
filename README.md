# AWS GLUE LOCAL SCALA

This is a tool to develop and test AWS Glue scripts written in Scala. It uses SBT to manage the necessary resources for local testing.

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

NOTE: Testing locally should be done with a small data set. Local executions do not have the parallelism benefits of a true distributed cluster. Instead, the local testing works on a single-node cluster.

### Deployment

In order to deploy your glue job, either copy and paste the contents of your tested script using the AWS console, or upload the script to S3 using your preferred deployment tool.
