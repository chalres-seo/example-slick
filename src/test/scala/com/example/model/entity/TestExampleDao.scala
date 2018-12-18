package com.example.model.entity

import com.example.model.persistent.record.ExampleRecord

class TestExampleDao extends CommonDaoCRUDTest[ExampleRecord] {
  override def getDao: CommonDao[ExampleRecord] = ExampleDao.getInstance

  override def getCreateExampleDataSet(exampleDataSetCount: Int): Vector[ExampleRecord] = {
    (1 to exampleDataSetCount).map { i =>
      ExampleRecord(i, i, s"test-col1-$i")
    }.toVector
  }

  override def getExampleRecord: ExampleRecord = {
    ExampleRecord(99, 99, s"test-col1-99")
  }

  override def getExampleRecordUpdated: ExampleRecord = {
    ExampleRecord(99, 100, s"test-col1-100")
  }
}
