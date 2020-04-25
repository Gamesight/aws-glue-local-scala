import org.scalatest._
import com.amazonaws.services.glue.util.GlueArgParser
import java.lang.Runtime
import java.lang.Process
import org.scalamock.scalatest.MockFactory
import com.amazonaws.services.glue.util.Job

class ExampleSpec extends FunSpec {
  describe("Example") {
    it("should run the job") {

      println("Running Job...")

      io.gamesight.AWSGlue.ExampleJob.main(Array(
        "--JOB_NAME", "job",
        "--stage", "dev",
        "--inputBucket", "<YOUR BUCKET NAME>",
        "--outputBucket", "<YOUR OUTPUT BUCKET NAME>",
        "--inputPrefix", "<YOUR INPUT PREFIX>",
        "--outputPrefix", "<YOUR OUTPUT PREFIX>"
      ))

    }
  }
}
