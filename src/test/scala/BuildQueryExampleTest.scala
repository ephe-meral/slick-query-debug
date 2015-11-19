import org.scalatest._
import java.util.UUID
import slick.driver.H2Driver.api._


class BuildQueryExampleTest extends FunSuite {
  val uuids = List[UUID]().padTo(10, UUID.randomUUID())

  test("force compilation of the slick query") {
    val completeQuery = BuildQueryExample.query(uuids)
    for (i <- 1 to 100) {
      println(s"Query Run $i: ${completeQuery.result.statements.head}")
    }
    assert(completeQuery.result.statements.head !== "")
  }
}
