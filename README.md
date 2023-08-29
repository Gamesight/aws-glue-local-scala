# AWS GLUE LOCAL SCALA

This is a template package to develop and test [AWS Glue] scripts written in Scala. It uses SBT to manage the necessary resources for local testing. 
It supports Glue 3.0

### Setup

Download the needed dependencies (scala, sbt, maven, **java JDK 8**). 

If you are using IntelliJ, get the [Scala plugin] and the [sbt plugin].

I highly recommend you install [scalafmt], to automatically format your code and keep it clean.

### Working with Scala and sbt
Scala is a modern programming language, which is compatible with Java. SBT (Scala Build Tool) is a build tool for Scala projects, used to manage dependencies, compile code, run tests, and package applications.

To interact with your project, cd to your root and run `sbt` to open the sbt shell. Once the shell is open, you can run:
- `compile`: Compile the source code
- `run`: Run the main application
- `test`: Run tests in the project
- `clean`: Clean project
- `reload`: reload project
- `update`: apply changes to the `build.sbt` file
- `reload; update;`: run consecutive commands at once
- `exit`: exit the shell

### Usage

Start with ExampleJob.scala as the boilerplate for development. There is some additional handling for stage separation that allows the script to run locally. The calls to `Job` functions should only run during executions within the AWS Glue environment. Additionally, when `dev` is passed to the `--stage` argument, the example adds local configuration to the `SparkContext` without affecting deployed executions. The example can be executed either locally or deployed, as long as the correct arguments are passed.

You should test your script locally with test cases, to avoid incurring costs by running the actual job to debug.

### Deployment

In order to deploy your glue job, you have 2 choices:
- Create a Glue ETL job in the AWS console, configure it to use Glue 3.0 and spark with Scala, then copy and paste the contents of your tested script into the job. 
  - Make sure you run `compile` before copy pasting the script.
  - Make sure you add the job argument `--stage` 
  - If you get NoClassDef errors, make sure you add the following argument for the job to find your class deinfition:
    `--class` with the value`com.fares.AWSGlue.GlueApp`
- Create the Glue ETL Job and upload the script to S3 using [AWS CDK]




[AWS Glue]: https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-scala.html
[Scala plugin]: https://plugins.jetbrains.com/plugin/1347-scala
[sbt plugin]: https://plugins.jetbrains.com/plugin/5007-sbt
[AWS CDK]: https://docs.aws.amazon.com/cdk/v2/guide/getting_started.html
[scalafmt]: https://scalameta.org/scalafmt/