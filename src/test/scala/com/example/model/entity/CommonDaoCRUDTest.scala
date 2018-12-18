package com.example.model.entity

import com.example.model.persistent.record.TableRecords
import com.example.utils.FutureUtils
import com.typesafe.scalalogging.LazyLogging
import org.hamcrest.CoreMatchers.is
import org.junit.{After, Assert, Before, Test}

trait CommonDaoCRUDTest[R <: TableRecords] extends LazyLogging {
  lazy val dao: CommonDao[R] = this.getDao

  val exampleDataSetCount: Int = 10
  lazy val exampleDataSet: Vector[R] = this.getCreateExampleDataSet(exampleDataSetCount)

  lazy val exampleRecord: R = this.getExampleRecord
  lazy val exampleRecordUpdated: R = this.getExampleRecordUpdated

  def getDao: CommonDao[R]
  def getCreateExampleDataSet(exampleDataSetCount: Int): Vector[R]

  def getExampleRecord: R
  def getExampleRecordUpdated: R

  @Before
  def setup(): Unit = {
    FutureUtils.awaitResultFuture(dao.create())
  }

  @After
  def clenup(): Unit = {
    FutureUtils.awaitResultFuture(dao.drop())
  }

  @Test
  def checkDDL(): Unit = {
    logger.info("check dll")
    logger.info(s"create dll:\n|\t${dao.getCreateDDL.mkString("\n|\t")}")
  }

  @Test
  def testSingleRecordCRU(): Unit = {
    val insertResult = FutureUtils.awaitResultFuture(dao.insert(exampleRecord))
    Assert.assertThat(insertResult.get, is(1))

    val records = this.getAllRecord
    Assert.assertThat(records.length, is(1))
    Assert.assertThat(records.head, is(exampleRecord))

    val updateResult = FutureUtils.awaitResultFuture(dao.insertOrUpdateRecord(exampleRecordUpdated))
    Assert.assertThat(updateResult.get, is(1))

    val recordsAfterUpdated = this.getAllRecord
    Assert.assertThat(recordsAfterUpdated.length, is(1))
    Assert.assertThat(recordsAfterUpdated.head, is(exampleRecordUpdated))
  }

  @Test
  def testMultiRecordCR(): Unit = {
    val insertResult = FutureUtils.awaitResultFuture(dao.insertAll(exampleDataSet))
    Assert.assertThat(insertResult.get, is(exampleDataSetCount))

    val records = this.getAllRecord
    Assert.assertThat(records.length, is(exampleDataSetCount))
  }

  private def getAllRecord: Vector[R] = {
    val records = FutureUtils.awaitResultFuture(dao.selectAllRecord).get.toVector

    logger.info("get all record")
    logger.info(s"records:\n|\t${records.mkString("\n|\t")}")

    records
  }
}