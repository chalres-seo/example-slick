package com.example.utils

import java.util.concurrent.TimeUnit

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import scala.concurrent.duration.Duration

object AppConfig extends LazyLogging {
  /** read custom application.conf */
//  private val conf: Config = this.readConfigFromFile("conf/application.conf")
  private val conf: Config = ConfigFactory.load().resolve()

  /** application config */
  val applicationName: String = conf.getString("application.name")
  val defaultWaitMillis = 3000L

  /** future config */
  val defaultFutureTimeout: Duration = Duration.apply(conf.getLong("application.futureTimeWait"), TimeUnit.MILLISECONDS)

  /** database config, from resource application config */
  val defaultFetchSize = 10000
  lazy val mainDbConfig = {
    logger.debug(s"database config: ${conf.getConfig("database.main")}")
    DatabaseConfig.forConfig[JdbcProfile]("database.main")
  }

  lazy val logDbConfig = {
    logger.debug(s"database config: ${conf.getConfig("database.log")}")
    DatabaseConfig.forConfig[JdbcProfile]("database.log")
  }

  lazy val statsDbConfig = {
    logger.debug(s"database config: ${conf.getConfig("database.stats")}")
    DatabaseConfig.forConfig[JdbcProfile]("database.stats")
  }
}