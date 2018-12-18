package com.example.model.persistent.table

import com.example.model.persistent.db.DBConfiguration
import slick.lifted.ProvenShape

private[model] trait ExampleTable extends DBConfiguration {
  import profile.api._
  import com.example.model.persistent.record.{ ExampleRecord => _TableRecord }

  val tableName: String = "TB_EXAMPLE"
  lazy val tableQuery: TableQuery[_Table] = TableQuery[_Table]

  private[table] class _Table(tag: Tag) extends Table[_TableRecord](tag, tableName) {
    def examplePK: Rep[Int] = column[Int]("example_pk", O.PrimaryKey)
    def exampleCol1: Rep[Int] = column[Int]("example_col1")
    def exampleCol2: Rep[String] = column[String]("example_col2", O.Length(255))
    override def * : ProvenShape[_TableRecord] = (examplePK, exampleCol1, exampleCol2) <>
      (_TableRecord.tupled, _TableRecord.unapply)
  }
}