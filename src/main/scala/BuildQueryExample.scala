import java.util.UUID
import slick.driver.H2Driver.api._


object BuildQueryExample {

  class TableA(tag: Tag) extends Table[(UUID, String, String, Boolean)](tag, "table_a") {
    def id = column[UUID]("id", O.DBType("UUID"), O.PrimaryKey)

    def active = column[Boolean]("active")

    def fieldA = column[String]("field_a")
    def fieldB = column[String]("field_b")

    def * = (id, fieldA, fieldB, active)
  }
  val tableA = TableQuery[TableA]


  class TableB(tag: Tag) extends Table[(UUID, UUID, String, String)](tag, "table_b") {
    def id = column[UUID]("id", O.DBType("UUID"), O.PrimaryKey)

    def tableAId = column[UUID]("table_a_id", O.DBType("UUID"))

    def fieldA = column[String]("field_a")
    def fieldB = column[String]("field_b")

    def * = (id, tableAId, fieldA, fieldB)
  }
  val tableB = TableQuery[TableB]


  def query(uuids: Seq[UUID]) =
    for {
      bVal <- tableB.filter { bVal => bVal.tableAId inSetBind uuids }
      aVal <- tableA
      if aVal.id === bVal.tableAId
      if aVal.active === true
    } yield { bVal }
}
