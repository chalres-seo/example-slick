package com.example.model.entity

import com.example.model.persistent.db.DBConfiguration
import com.example.model.persistent.record.TableRecords
import com.example.utils.AppConfig
import com.typesafe.scalalogging.LazyLogging
import slick.basic.DatabasePublisher

import scala.concurrent.Future
import scala.util.{Failure, Try}

trait CommonDao[R <: TableRecords] extends LazyLogging with DBConfiguration {
  def runTryHandler[A, B](runTry: Try[A], logMsg: String)(whenSuccess: A => B): Try[B] = {
    runTry.recoverWith {
      case e:Exception =>
        logger.error(s"DB IO action failed $logMsg, msg: ${e.getMessage}")
        Failure(e)
    }.map { runResult =>
      logger.info(s"DB IO action succeed $logMsg.")
      whenSuccess(runResult)
    }
  }

  def runTryHandler[A](runTry: Try[A], logMsg: String): Try[A] = {
    runTry.recoverWith {
      case e:Exception =>
        logger.error(s"DB IO action failed $logMsg, msg: ${e.getMessage}")
        Failure(e)
    }.map { runResult =>
      logger.info(s"DB IO action succeed $logMsg.")
      runResult
    }
  }

  /** common action */

  def getCreateDDL: Iterator[String]
  def getDropDDL: Iterator[String]
  def truncateDDL: Iterator[String]
  def create(): Future[Try[Unit]]
  def drop(): Future[Try[Unit]]
  def truncate(): Future[Try[Unit]]
  def insert(record: R): Future[Try[Int]]
  def insertAll(records: Vector[R]): Future[Try[Int]]
  def insertOrUpdateRecord(record: R): Future[Try[Int]]
  def selectAllRecord: Future[Try[Seq[R]]]
  def streamRecords(fetchRowSize: Int = AppConfig.defaultFetchSize): DatabasePublisher[R]
  def closeDbConn(): Unit
}