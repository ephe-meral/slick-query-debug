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


  /*
  for {
    aValues <- tableA
    if
    aliases <- productIdsQuery(noRedirectQuery(Tables.aliasTable), productIds)
    dbProduct <- Tables.productTable
    if dbProduct.id === aliases.productId
    if dbProduct.comparableFeatures.+>>(ActivationStatusFeature.id) =!= ActivationStatus.INACTIVE.toString
    dbVendor <- Tables.vendorTable
    if dbVendor.id === dbProduct.vendorId
    if dbVendor.features.+>>(ActivationStatusFeature.id) =!= ActivationStatus.INACTIVE.toString
  } yield { aliases }

  def noRedirectQuery(query: Query[AliasTable, AliasRow, Seq]) = {
    query.filter(at => at.redirect.isEmpty)
  }

  def filterAliasQuery(query: Query[AliasTable, AliasRow, Seq], namespace: String, alias: String) = {
    query.filter { aliasRow => aliasRow.namespace === namespace && aliasRow.alias === alias }
  }

  def productIdsQuery(query: Query[AliasTable, AliasRow, Seq], productIds: Seq[ProductId]) = {
    val uuids = productIds.map(_.uuid)
    query.filter(at => at.productId inSetBind uuids)
  }
  */
}
